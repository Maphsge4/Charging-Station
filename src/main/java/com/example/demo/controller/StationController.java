package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.commonutils.Response;
import com.example.demo.entity.Account;
import com.example.demo.entity.Car;
import com.example.demo.entity.Station;
import com.example.demo.service.StationService;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * (Station)表控制层
 *
 * @author makejava
 * @since 2022-06-15 23:23:01
 */
@RestController
@RequestMapping("/dataservice/station")
@CrossOrigin
public class StationController {
    @Autowired
    private StationService stationService;

    @Autowired
    private CarService carService;

    @GetMapping("open")
    public Response open(String id) {

        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        Station station = new Station();
        station.setState(1);
        stationService.update(station, wrapper);

        return Response.ok();
    }

    @GetMapping("close")
    public Response close(String id) {

        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        Station station = new Station();
        station.setState(0);
        stationService.update(station, wrapper);

        return Response.ok();
    }

    @GetMapping("breakdown")
    public Response breakdown(Integer id, Integer need) {
//        System.out.println(id);
        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

//        System.out.println(id);
        int md;
        Station station = new Station();
        station.setState(need);
        stationService.update(station, wrapper);

        if(need == 0)
        {
            QueryWrapper<Car> Qwrapper = new QueryWrapper<>();
            Qwrapper.between("pos", id * 10, id * 10 + 9);

            List<Car>  carList2 =  carService.list(Qwrapper);
            for ( Car i : carList2){
                Car tmpCar = new Car();
                tmpCar.setPos(-1*(i.getPos()));
                Qwrapper = new QueryWrapper<>();
                Qwrapper.eq("car_id",i.getCarId());

                carService.update(tmpCar,Qwrapper);
            }
        }
        else if(need == 1)
        {
            wrapper = new QueryWrapper<>();
            wrapper.eq("id",id);
            Station tmpStation = stationService.getOne(wrapper);
            md = tmpStation.getType();


            QueryWrapper<Car> Qwrapper = new QueryWrapper<>();
            Qwrapper.between("pos",-100,0).eq("mode",md).orderByDesc();
            List<Car> fuList = new ArrayList<>();
            fuList = carService.list(Qwrapper);

            int j = 1;
            int res = carService.count(Qwrapper);
            if(res != 0)
            {
                for(Car i: fuList){
                    if(j>3) break;
                    Car hhhCar = new Car();
                    hhhCar.setPos(id * 10 + j);

                    Qwrapper = new QueryWrapper<>();
                    Qwrapper.eq("car_id",i.getCarId());
                    carService.update(hhhCar,Qwrapper);

                    j++;
                    if(j>3) break;
                }

            }

            Qwrapper = new QueryWrapper<>();
            Qwrapper.between("pos",100,150).eq("mode",md).orderByAsc();
            fuList = carService.list(Qwrapper);

            for(Car i: fuList){
                if(j>3) break;
                Car hhhCar = new Car();
                hhhCar.setPos(id * 10 + j);

                Qwrapper = new QueryWrapper<>();
                Qwrapper.eq("car_id",i.getCarId());
                carService.update(hhhCar,Qwrapper);

                j++;
                if(j>3) break;
            }
        }
        return Response.ok();
    }

    @PostMapping("state")
    public Response state() {

        List<Station> stationList = stationService.list(null);
        return Response.ok().data("result", stationList);
    }

}

