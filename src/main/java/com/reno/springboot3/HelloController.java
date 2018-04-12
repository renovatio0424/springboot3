package com.reno.springboot3;

import com.reno.springboot3.repositories.MyData;
import com.reno.springboot3.repositories.MyDataDaoImpl;
import com.reno.springboot3.repositories.MyDataRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    @PostConstruct
    public void init(){
        dataDao = new MyDataDaoImpl(entityManager);
        MyData myData = new MyData();
        myData.setName("kim");
        myData.setAge(123);
        myData.setMail("kim@gilbut.co.kr");
        myData.setMemo("0909999999");
        repository.save(myData);

        MyData myData1 = new MyData();
        myData1.setName("lee");
        myData1.setAge(15);
        myData1.setMail("klee@flower");
        myData1.setMemo("0808888888");
        repository.save(myData1);

        MyData myData2 = new MyData();
        myData2.setName("choi");
        myData2.setAge(37);
        myData2.setMail("choi@happy");
        myData2.setMemo("0707777777");
        repository.save(myData2);
    }

    @Autowired
    MyDataRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    MyDataDaoImpl dataDao;

//    @RequestMapping("/repository")
//    public ModelAndView showAllitems(ModelAndView modelAndView){
//        modelAndView.setViewName("repository");
//        Iterable<MyData> list = repository.findAll();
//        modelAndView.addObject("data",list);
//        return modelAndView;
//    }

    @RequestMapping(value = "/repository", method = RequestMethod.GET)
    public ModelAndView getAllitems(@ModelAttribute("formModel") MyData myData, ModelAndView modelAndView){
        modelAndView.setViewName("repository_index");
        modelAndView.addObject("msg","MyData 예제입니다");
        Iterable<MyData> list = dataDao.getAll();
        modelAndView.addObject("datalist",list);
        return modelAndView;
    }

    @RequestMapping(value = "/repository", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView formItem(@ModelAttribute("formModel") @Validated MyData myData,
                                 BindingResult result,
                                 ModelAndView modelAndView){
        ModelAndView res = null;
        if(!result.hasErrors()){
            repository.saveAndFlush(myData);
            res = new ModelAndView("redirect:/repository");
        } else {
            modelAndView.setViewName("repository_index");
            modelAndView.addObject("msg", "sorry, error is occurred...");
            Iterable<MyData> list = repository.findAll();
            modelAndView.addObject("datalist",list);
            res = modelAndView;
        }
        return res;
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@ModelAttribute MyData myData, @PathVariable int id, ModelAndView modelAndView){
        modelAndView.setViewName("edit");
        modelAndView.addObject("title","edit mydata.");
        MyData data = repository.findByid((long) id);
        modelAndView.addObject("formModel", data);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView update(@ModelAttribute MyData myData, ModelAndView modelAndView){
        repository.saveAndFlush(myData);
        return new ModelAndView("redirect:/repository");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable int id, ModelAndView modelAndView){
        modelAndView.setViewName("delete");
        modelAndView.addObject("title","delete mydata.");
        MyData data = repository.findByid((long) id);
        modelAndView.addObject("formModel",data);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ModelAndView remove(@RequestParam long id, ModelAndView modelAndView){
        repository.deleteById(id);
        return new ModelAndView("redirect:/repository");
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView find(ModelAndView modelAndView){
        modelAndView.setViewName("find");
        modelAndView.addObject("title", "Find Page");
        modelAndView.addObject("msg", "MyData의 예제입니다");
        modelAndView.addObject("value","");
        Iterable<MyData> list = dataDao.getAll();
        modelAndView.addObject("datalist", list);
        return modelAndView;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest reqeRequest, ModelAndView modelAndView){
        modelAndView.setViewName("find");
        String param = reqeRequest.getParameter("fstr");

        if(param == "")
            modelAndView = new ModelAndView("redirect:/find");
        else {
            modelAndView.addObject("title", "Find result");
            modelAndView.addObject("msg", "[" + param + "] 의 검색 결과");
            modelAndView.addObject("value", param);
            List<MyData> list = dataDao.find(param);
            modelAndView.addObject("datalist",list);
        }

        return modelAndView;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        modelAndView.addObject("title","Find Page");
        Iterable<MyData> list = dataDao.findByAge(10,40);
        modelAndView.addObject("datalist",list);
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
