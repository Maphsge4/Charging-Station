package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.commonutils.Response;
import com.example.demo.entity.Account;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Car;
import com.example.demo.entity.Station;
import com.example.demo.service.BillService;
import com.example.demo.service.CarService;
import com.example.demo.service.StationService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Car)表控制层
 *
 * @author makejava
 * @since 2022-06-15 23:23:01
 */
@RestController
@RequestMapping("/dataservice/car")
@CrossOrigin
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private StationService stationService;

    @Autowired
    private BillService billService;

    @PostMapping("state")
    public Response state(String username) {

        QueryWrapper<Car> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).select("username,car_id,state");

        List<Car> carList = carService.list(wrapper);
        List<Object> a = new ArrayList<>();
        for (Car i : carList) {
            HashMap<String,Object> data = new HashMap<>();
            data.put("car_id",i.getCarId());
            data.put("state",i.getState());
            a.add(data);
        }

        return Response.ok().data("list", a);
    }

    @Value("${var.pos}")
    private Integer pos;

    @GetMapping("apply") // 这儿差一个调度。申请过来肯定是直接走调度算法的
    public Response apply(String car_id, Integer need, Integer mode) {

        int modeInt ;
        int a1,a2,a3,a4,a5;
        double t1 = 0,t2 = 0,t3 = 0,t4 = 0,t5 = 0;
        if(mode == 1) {
            Car car = new Car();
            car.setUsername("Maphsge7");
            car.setCarId(car_id);
            car.setNeed(need);
            modeInt = 1;
            car.setMode(modeInt);
            car.setState(-1);

            // 这儿需要计算队列有没有空位置
            QueryWrapper<Car> wrapper = new QueryWrapper<>();
            wrapper.between("pos", 10, 29);
            if(carService.count(wrapper)==6)
            {
                car.setState(3);
                car.setPos(++pos);
                System.out.println(pos);
            }
            else
            {
                wrapper = new QueryWrapper<>();
                wrapper.between("pos", 10, 15);
                a1 = carService.count(wrapper);

                wrapper = new QueryWrapper<>();
                wrapper.between("pos", 20, 25);
                a2 = carService.count(wrapper);

                QueryWrapper<Station> stationQueryWrapper = new QueryWrapper<>();
                stationQueryWrapper.eq("type",mode);
                List<Station> tmpList = stationService.list(stationQueryWrapper);

                for(Station i : tmpList){
                    if(i.getId()==1 && i.getState() == 0)
                    {
                        a1 = 3;
                    }
                    if(i.getId()==2 && i.getState() == 0)
                    {
                        a2 = 3;
                    }
                }
                if(a1 == 3 && a2 == 3)
                {
                    car.setState(3);
                    car.setPos(++pos);
                    System.out.println(pos);
                }
                else if(a1 == 3 && a2 < 3){
                    if(a2 == 0){
                        car.setState(1);
                        car.setPos(21);
                    }
                    else{
                        car.setState(2);
                        car.setPos(21+a2);
                    }
                }
                else if (a1 < 3 && a2 == 3)
                {
                    if(a1 == 0){
                        car.setState(1);
                        car.setPos(11);
                    }
                    else{
                        car.setState(2);
                        car.setPos(11+a1);
                    }
                }
                else if(a1<3 && a2<3)
                {
                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 10, 15);
                    List<Car> list = carService.list(wrapper);
                    for (Car i : list) {
                        t1 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 20, 25);
                    List<Car> list2 = carService.list(wrapper);
                    for (Car i : list2) {
                        t2 += i.getNeed()- i.getAlready();
                    }

                    if(t1 <= t2)
                    {
                        if(t1 == 0){
                            car.setState(1);
                            car.setPos(11);
                        }
                        else{
                            car.setState(2);
                            car.setPos(11+a1);
                        }
                    }
                    else
                    {
                        if(t2 == 0){
                            car.setState(1);
                            car.setPos(21);
                        }
                        else{
                            car.setState(2);
                            car.setPos(21+a2);
                        }
                    }
                }
            }
            carService.save(car);
        }
        else if(mode == 0){
            Car car = new Car();
            car.setUsername("Maphsge7");
            car.setCarId(car_id);
            car.setNeed(need);
            modeInt = 0;
            car.setMode(modeInt);
            car.setState(-1);

            // 这儿需要计算队列有没有空位置
            QueryWrapper<Car> wrapper = new QueryWrapper<>();
            wrapper.between("pos", 30, 59);
            if(carService.count(wrapper)==9)
            {
                car.setState(3);
                car.setPos(++pos);
                System.out.println(pos);
            }
            else
            {
                wrapper = new QueryWrapper<>();
                wrapper.between("pos", 30, 35);
                a3 = carService.count(wrapper);

                wrapper = new QueryWrapper<>();
                wrapper.between("pos", 40, 45);
                a4 = carService.count(wrapper);

                wrapper = new QueryWrapper<>();
                wrapper.between("pos", 50, 55);
                a5 = carService.count(wrapper);

                QueryWrapper<Station> stationQueryWrapper = new QueryWrapper<>();
                stationQueryWrapper.eq("type",mode);
                List<Station> tmpList = stationService.list(stationQueryWrapper);

                for(Station i : tmpList){
                    if(i.getId()==3 && i.getState() == 0)
                    {
                        a3 = 3;
                    }
                    if(i.getId()==4 && i.getState() == 0)
                    {
                        a4 = 3;
                    }
                    if(i.getId()==5 && i.getState() == 0)
                    {
                        a5 = 3;
                    }
                }

                if(a3 == 3 && a4 == 3 && a5 == 3)
                {
                    car.setState(3);
                    car.setPos(++pos);
                    System.out.println(pos);
                }
                else if(a3 == 3 && a4 == 3 && a5<3){
                    if(a5 == 0){
                        car.setState(1);
                        car.setPos(51);
                    }
                    else{
                        car.setState(2);
                        car.setPos(51+a5);
                    }
                }
                else if (a3 == 3 && a4 < 3 && a5==3)
                {
                    if(a4 == 0){
                        car.setState(1);
                        car.setPos(41);
                    }
                    else{
                        car.setState(2);
                        car.setPos(41+a4);
                    }
                }
                else if (a3 < 3 && a4 == 3 && a5==3){
                    if(a3 == 0){
                        car.setState(1);
                        car.setPos(31);
                    }
                    else{
                        car.setState(2);
                        car.setPos(31+a3);
                    }
                }
                else if(a3 == 3 && a4 < 3 && a5<3){
                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 40, 45);
                    List<Car> list = carService.list(wrapper);
                    for (Car i : list) {
                        t4 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 50, 55);
                    List<Car> list2 = carService.list(wrapper);
                    for (Car i : list2) {
                        t5 += i.getNeed()- i.getAlready();
                    }

                    if(t4 <= t5)
                    {
                        if(t4 == 0)
                        {
                            car.setState(1);
                            car.setPos(41);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(41+a4);
                        }
                    }
                    else
                    {
                        if(t5 == 0)
                        {
                            car.setState(1);
                            car.setPos(51);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(51+a5);
                        }
                    }
                }
                else if(a3 < 3 && a4 == 3 && a5<3){
                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 30, 35);
                    List<Car> list = carService.list(wrapper);
                    for (Car i : list) {
                        t3 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 50, 55);
                    List<Car> list2 = carService.list(wrapper);
                    for (Car i : list2) {
                        t5 += i.getNeed()- i.getAlready();
                    }

                    if(t3 <= t5)
                    {
                        if(t3 == 0)
                        {
                            car.setState(1);
                            car.setPos(31);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(31+a3);
                        }
                    }
                    else
                    {
                        if(t5 == 0)
                        {
                            car.setState(1);
                            car.setPos(51);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(51+a5);
                        }
                    }
                }
                else if(a3 < 3 && a4 < 3 && a5==3){
                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 30, 35);
                    List<Car> list = carService.list(wrapper);
                    for (Car i : list) {
                        t3 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 40, 45);
                    List<Car> list2 = carService.list(wrapper);
                    for (Car i : list2) {
                        t4 += i.getNeed()- i.getAlready();
                    }

                    if(t3 <= t4)
                    {
                        if(t3 == 0)
                        {
                            car.setState(1);
                            car.setPos(31);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(31+a3);
                        }
                    }
                    else
                    {
                        if(t4 == 0)
                        {
                            car.setState(1);
                            car.setPos(41);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(41+a4);
                        }
                    }
                }
                else if(a3 < 3 && a4 < 3 && a5<3)
                {
                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 30, 35);
                    List<Car> list = carService.list(wrapper);
                    for (Car i : list) {
                        t3 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 40, 45);
                    List<Car> list2 = carService.list(wrapper);
                    for (Car i : list2) {
                        t4 += i.getNeed()- i.getAlready();
                    }

                    wrapper = new QueryWrapper<>();
                    wrapper.between("pos", 50, 55);
                    List<Car> list3 = carService.list(wrapper);
                    for (Car i : list3) {
                        t5 += i.getNeed()- i.getAlready();
                    }

                    if(t3 <=t4 && t3 <= t5)
                    {
                        if(t3 == 0)
                        {
                            car.setState(1);
                            car.setPos(31);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(31+a3);
                        }
                    }
                    else if (t4 <= t3 && t4 <= t5)
                    {
                        if(t4 == 0)
                        {
                            car.setState(1);
                            car.setPos(41);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(41+a4);
                        }
                    }
                    else if (t5 <= t3 && t5 <= t4)
                    {
                        if(t5 == 0)
                        {
                            car.setState(1);
                            car.setPos(51);
                        }
                        else
                        {
                            car.setState(2);
                            car.setPos(51+a5);
                        }
                    }
                }
            }
            carService.save(car);
        }
        else if(mode == -1){
            QueryWrapper<Car> wrapper = new QueryWrapper<>();
            wrapper.eq("car_id", car_id);

            carService.remove(wrapper);
        }

        return Response.ok();
    }


    @GetMapping("change")
    public Response change(String car_id, Integer need, Integer mode) {
        QueryWrapper<Car> wrapper = new QueryWrapper<>();
        wrapper.eq("car_id", car_id);

        Car orgCar = carService.getOne(wrapper);
        int ppos = orgCar.getPos();

        if(ppos < 100)
        {
            return Response.fail();
        }
        else {
            Car car = new Car();
            if (need != -1) {
                car.setNeed(need);
            }
            if (mode != -1) {
                car.setMode(mode);
            }
            car.setPos(++pos);
            carService.update(car, wrapper);

            return Response.ok();
        }
    }

//    @PostMapping("terminate")
//    public Response terminate(Integer car_id) {
//        QueryWrapper<Car> wrapper = new QueryWrapper<>();
//        wrapper.eq("car_id", car_id);
//
//        carService.remove(wrapper);
//
//        return Response.ok();
//    }

    @GetMapping("length")
    public Response lineLength(Integer car_id) {
        QueryWrapper<Car> wrapper = new QueryWrapper<>();
        wrapper.eq("car_id", car_id);

        Car car = carService.getOne(wrapper);
        Integer pos = car.getPos();
        if(pos >100)
        {
            return Response.ok().data("num",3);
        }
        else if(pos>10)
        {
            Integer tmp = pos % 10 -1;
            return Response.ok().data("num",tmp);
        }

        return Response.ok();
    }

    @GetMapping("lineId")
    public Response lineId(Integer car_id) {
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("car_id" , car_id);

        List<Car> list = carService.list(queryWrapper);
        List<Object> a = new ArrayList<>();
        for (Car i : list) {
            HashMap<String,Object> data = new HashMap<>();
            data.put("waiting_number",i.getId());
//            data.put("need",i.getNeed());
            a.add(data);
        }
        return Response.ok().data("list" ,a);
    }

    @PostMapping("waitingInfo")
    public Response waitingInfo(Integer cha_id) {
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        int tmp = cha_id * 10 + 1;
        queryWrapper.between("pos", tmp, tmp+6);

        List<Car> list = carService.list(queryWrapper);
        List<Object> a = new ArrayList<>();
        for (Car i : list) {
            HashMap<String,Object> data = new HashMap<>();
            data.put("username",i.getUsername());
            data.put("need",i.getNeed());
            a.add(data);
        }

        return Response.ok().data("list" ,a);
    }
}

