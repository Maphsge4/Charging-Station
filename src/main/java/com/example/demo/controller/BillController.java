package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.commonutils.Response;
import com.example.demo.entity.Account;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Car;
import com.example.demo.service.CarService;
import com.example.demo.entity.Station;
import com.example.demo.service.BillService;
import com.example.demo.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dataservice/info")
@CrossOrigin
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private CarService carService;

    @GetMapping("detail")
    public Response detail(String car_id) {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",car_id);

        List<Bill> list = billService.list(queryWrapper);
        return Response.ok().data("list" ,list);
    }

    @PostMapping("statistics")
    public Response statistics(String beginTime, String endTime) {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
//        System.out.println(beginTime);
        queryWrapper.between("gena_time", beginTime, endTime);

        List<Bill> list = billService.list(queryWrapper);
        return Response.ok().data("list" ,list);
    }


    @Value("${var.hour}")
    private Integer hour;

    @Value("${var.fupos}")
    private Integer fupos;

    @Value("${var.minute}")
    private Integer minute;

    //    @Scheduled(fixedRate = 30000)
    @GetMapping("changeTime")
    public Response demo() {


        QueryWrapper<Car> wrapper = new QueryWrapper<>();
        QueryWrapper<Bill> Qwrapper = new QueryWrapper<>();

        List<Car> list = carService.list(wrapper);
        for (Car i : list) {
            double sur = 0;
            boolean e = true ;
            if(i.getPos()>0 && i.getPos()<100 && (i.getPos()%10 == 1)){
                Car newCar = new Car();
                e = true;
                if(i.getMode() == 1)
                {
                    newCar.setAlready(i.getAlready()+2.5);
                    sur = 2.5;
                    if(newCar.getAlready()>=i.getNeed())
                    {
                        double m = i.getNeed();
                        sur = i.getNeed() - i.getAlready();
                        newCar.setAlready(m);
                        e = false;
                    }
                }
                else if(i.getMode() == 0)
                {
                    newCar.setAlready(i.getAlready()+0.833);
                    sur = 0.833;
                    if(newCar.getAlready()>=i.getNeed())
                    {
                        double m = i.getNeed();
                        sur = i.getNeed() - i.getAlready();
                        newCar.setAlready(m);
                        e = false;
                    }
                }

                wrapper = new QueryWrapper<>();
                wrapper.eq("car_id",i.getCarId());
                carService.update(newCar,wrapper);

                if(!e)
                {
                    wrapper = new QueryWrapper<>();
                    wrapper.eq("car_id",i.getCarId());

                    int postmp = (i.getPos() / 10) * 10;
                    int md = 0;
                    md = i.getMode();

                    carService.remove(wrapper);

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos",postmp,postmp+9).eq("mode",md);

                    List<Car> othList = new ArrayList<>();
                    othList = carService.list(wrapper);
                    for(Car j : othList)
                    {
                        Car tmpCar = new Car();
                        tmpCar.setPos(j.getPos()-1);
                        wrapper = new QueryWrapper<>();
                        wrapper.eq("car_id",j.getCarId());
                        carService.update(tmpCar,wrapper);
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos",-100,0).eq("mode",md).orderByDesc().last("limit 1");
                    int res = carService.count(wrapper);
                    if(res == 0)
                    {
                        wrapper = new QueryWrapper<>();
                        wrapper.between("pos",100,110).eq("mode",md).orderByAsc().last("limit 1");
                        Car car = carService.getOne(wrapper);
                        Car scheCar = new Car();
                        scheCar.setPos(postmp+3);
                        carService.update(scheCar,wrapper);
                    }else {
                        Car car = carService.getOne(wrapper);
                        Car scheCar = new Car();
                        scheCar.setPos(postmp + 3);
                        carService.update(scheCar, wrapper);
                    }
                }
            }
        }

        wrapper = new QueryWrapper<>();
        wrapper.select("car_id,pos,mode").orderByAsc("pos");

        List<Car> carList = carService.list(wrapper);

        List<HashMap<String,Object> > cha = new ArrayList<>();

        int Fwt = 0,Twt = 0;

        for (Car i : carList) {
            HashMap<String,Object> data = new HashMap<>();
            HashMap<String,Integer> data4 = new HashMap<>();
            if(i.getPos()<100){
                data.put("id",i.getPos()/10);
                data.put("num",i.getCarId());
                cha.add(data);
            }
            else {

                if(i.getMode() == 1)
                {
                    Fwt ++ ;
                }
                else if(i.getMode() == 0)
                {
                    Twt ++ ;
                }
            }
        }
        HashMap<String,Object> data2 = new HashMap<>();
        data2.put("Fwaiting",Fwt);
        data2.put("Twaiting",Twt);

        minute += 5;
        if(minute == 60)
        {
            hour ++;
            minute = 0;
        }

        System.out.printf("hour = %d , minute = %d\n",hour,minute);


        return Response.ok().data("ChargingData", cha).data("WaitingData" , data2).data("hour",hour).data("minute",minute);


    }

}
