package com.example.demo.service.impl;

import com.example.demo.entity.Bill;
import com.example.demo.mapper.BillMapper;
import com.example.demo.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author machenxiang
 * @since 2022-04-17
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

}