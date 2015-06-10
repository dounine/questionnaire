package com.survey.service.sys.impl;

import com.survey.service.sys.TaskDemoServiceI;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class TaskDemoServiceImpl
  implements TaskDemoServiceI
{
  public void test()
  {
    System.out.println("定时任务执行...");
  }
}