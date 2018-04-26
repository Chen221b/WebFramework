package com.example.controller;

import com.example.model.Example;
import com.example.service.ExampleService;
import com.qiu221b.framework.DispatcherServlet;
import com.qiu221b.framework.annotation.Action;
import com.qiu221b.framework.annotation.Controller;
import com.qiu221b.framework.annotation.Inject;
import com.qiu221b.framework.bean.Param;
import com.qiu221b.framework.bean.View;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExampleController {
    @Inject
    private ExampleService exampleService;

    @Action("get:/example")
    public View index(Param param){
        Example example1 = new Example("Damon", 12);
        List<Example> exampleList = new ArrayList<>();
        exampleList.add(example1);
        return new View("index.jsp").addModel("exampleList", exampleList);
    }
}
