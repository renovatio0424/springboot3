package com.reno.springboot3;

import com.reno.springboot3.repositories.MyDataRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HelloController {
    @Autowired
    MyDataRepository repository;

    @RequestMapping("/repository")
    public ModelAndView showAllitems(ModelAndView modelAndView){
        modelAndView.setViewName("repository");
        Iterable<MyData> list = repository.findAll();
        modelAndView.addObject("data",list);
        return modelAndView;
    }


    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index2");
        modelAndView.addObject("msg","current data.");
        DataObject object = new DataObject(123,"lee","lee@flower.com");
        modelAndView.addObject("object",object);
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView index(@PathVariable int id, ModelAndView modelAndView){
        modelAndView.setViewName("index3");
        modelAndView.addObject("id",id);
        modelAndView.addObject("check",id >= 0);
        modelAndView.addObject("trueVal","Positive!");
        modelAndView.addObject("falseVal", "Negative ...");
        return modelAndView;
    }

    @RequestMapping("/season/{month}")
    public ModelAndView findSeason(@PathVariable int month, ModelAndView modelAndView){
        modelAndView.setViewName("season");
        int m = Math.abs(month) % 12;
        m = m == 0 ? 12 : m;
        modelAndView.addObject("month",m);
        modelAndView.addObject("check",Math.floor(m / 3));
        return modelAndView;
    }

    @RequestMapping("/phonebook")
    public ModelAndView showPhoneNumberTable(ModelAndView modelAndView){
        modelAndView.setViewName("phoneNumberTable");
        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"park", "park@yamada.com","090-999-9999"});
        data.add(new String[]{"lee", "lee@flower.com", "080-888-8888"});
        data.add(new String[]{"choi","choi@happy.com", "070-777-7777"});
        modelAndView.addObject("data",data);
        return modelAndView;
    }

    @RequestMapping("/phonebook/{num}")
    public ModelAndView findPhoneNumber(@PathVariable int num, ModelAndView modelAndView){
        modelAndView.setViewName("phoneNumberResult");
        modelAndView.addObject("num",num);
        if(num >= 0)
            modelAndView.addObject("check", "num >= data.size() ? 0 : num");
        else
            modelAndView.addObject("check","num <= data.size() * -1 ? 0 : num * -1");

        ArrayList<DataObject> dataObjects = new ArrayList<>();
        dataObjects.add(new DataObject(0,"park", "park@yamada.com"));
        dataObjects.add(new DataObject(1,"lee","lee@flower.com"));
        dataObjects.add(new DataObject(2,"choi","choi@happy.com"));
        modelAndView.addObject("data",dataObjects);

        return modelAndView;
    }

    @RequestMapping("/tax/{tax}")
    public ModelAndView calculateTax(@PathVariable int tax, ModelAndView modelAndView){
        modelAndView.setViewName("calculateTax");
        modelAndView.addObject("tax",tax);
        return modelAndView;
    }

    @RequestMapping("/fragment")
    public ModelAndView showFragment(ModelAndView modelAndView){
        modelAndView.setViewName("fragmentIndex");
        return modelAndView;
    }

    @RequestMapping("/other")
    public String other(){
        return "redirect:/";
    }

    @RequestMapping("/home")
    public String home(){
        return "forward:/";
    }

    class DataObject{
        @Getter @Setter private int id;
        @Getter @Setter private String name;
        @Getter @Setter private String value;

        public DataObject(int id, String name, String value){
            super();
            this.id = id;
            this.name = name;
            this.value = value;
        }
    }
}
