package com.kuliza.demo.controller;

import com.kuliza.demo.implementations.CheckRegex;
import com.kuliza.demo.model.Risk_Details;
import com.kuliza.demo.model.Risk_Policy;
import com.kuliza.demo.model.UserPolicy;
import com.kuliza.demo.model.UserTestingLogs;
import com.kuliza.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@EnableScheduling
// public class newCon {

//     @Autowired
//     private UserRepository repo;

//     @Autowired
//     private RiskRepository riskRepo;

//     @Autowired
//     private UserPolicyRepository policyRepo;

//     @Autowired
//     private JavaMailSender javaMailSender;

//     @Autowired
//     private UserTestingLogsRepository userTestingLogsRepo;

//     @Autowired
//             private Risk_PolicyRepository risk_policyRepository;


//     String violatedPolicy = "";
//     String violatedRiks = "";



//     String user_name="";

//     @RequestMapping("/check")
//     public String checkForRisk(@AuthenticationPrincipal UserDetails ud, Model model) throws IOException {

//         user_name=ud.getUsername();
//         checkRisk();
//         List<UserTestingLogs> list=userTestingLogsRepo.findByUsername(user_name);
//         model.addAttribute("getViolatedDetails",list);
//         return "ShowResult";
//     }

//     @Scheduled(fixedDelay = 120000)
//     public void checkRisk() throws IOException {
// //        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         CheckRegex cr = new CheckRegex();
//         HashMap<String, Integer> map = new HashMap<>();

//         String dir = "/home/kuliza-568/Downloads/demo/uploads/" + user_name;
//         File folder = new File(dir);
//         File[] listOfFiles = folder.listFiles();
//         if (listOfFiles.length > 0)
//         {

//             for (int j = 0; j < listOfFiles.length; j++) {
//                 String str1 = "";
//                 violatedPolicy="";
//                 violatedRiks="";
//                 if (listOfFiles[j].isFile())
//                 {

// //                    long time =listOfFiles[j].lastModified();
// //                    System.out.println(time);
//                     FileInputStream fis = new FileInputStream(listOfFiles[j]);
//                     InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset());
//                     BufferedReader br = new BufferedReader(isr);
//                     String line;
//                     while ((line = br.readLine()) != null) {
//                         str1 = str1 + line;
//                     }
//                     br.close();

//                     System.out.println("...................");
//                     System.out.println("Content of the testing document ");
//                     List<String> words = Arrays.asList(str1.split(" "));
//                     for (String s : words) System.out.println(s);
//                     System.out.println("...................");

//                     map.clear();
//                     for (String s : words) {
//                         map.put(s, map.getOrDefault(s, 0) + 1);
//                     }

//                     List<UserPolicy> listPolicy = policyRepo.getEveryPolicy(user_name);

//                     for (UserPolicy up1 : listPolicy)
//                     {
//                         List<Risk_Policy> listRiskPolicy=risk_policyRepository.findByPolicyName(up1.getPolicy_name());
//                         List<Long> riskId = new ArrayList<>();
//                         for(Risk_Policy rp:listRiskPolicy)
//                         {
//                             riskId.add(rp.getRisk_id());
//                         }
//                         for (Long i : riskId)
//                         {
//                             List<Risk_Details> getKeyWords = riskRepo.findAllKeyWords(i);
//                             for (Risk_Details rd : getKeyWords)
//                             {
//                                 int cnt1 = 0;
//                                 String str = rd.getRisk_keyword();
//                                 String strRegex = rd.getRisk_regex();
//                                 List<String> riskKey = Arrays.asList(str.split(","));
//                                 List<String> regexKey = Arrays.asList(strRegex.split(","));
//                                 for (String s : riskKey)
//                                 {
//                                     if (s.length() == 0) continue;
//                                     if (map.containsKey(s)) cnt1 += map.get(s);

//                                 }
//                                 for (String s : regexKey)
//                                 {
//                                     if (s.length() == 0) continue;
//                                     cnt1 += cr.checkR(s, map);
//                                 }


//                                 if (cnt1 >= rd.getMatch_count())
//                                 {
//                                     System.out.println("Risk With id " + rd.getRisk_id() + " exceed the match count thus policy with policy name " + up1.getPolicy_name() + " is violated");
//                                     violatedPolicy = violatedPolicy + up1.getPolicy_name() + ",";
//                                     violatedRiks = violatedRiks + rd.getRisk_id() + ",";
//                                     UserTestingLogs utl = new UserTestingLogs();
//                                     utl.setRisk_id(i+"");
//                                     utl.setPolicy_name(up1.getPolicy_name());
//                                     utl.setDate(java.time.LocalDate.now() + "");
//                                     utl.setTime(java.time.LocalTime.now() + "");
//                                     utl.setUser_name(user_name);
//                                     userTestingLogsRepo.save(utl);
//                                 } else
//                                     System.out.println("No risk exceed the match count with risk id " + rd.getRisk_id());
//                             }
//                         }

//                         System.out.println("Scanning for next policy...");
//                         for (int i = 0; i < 4; i++) System.out.println(".");
//                     }
//                     System.out.println("No policies remaining");
// //                    if (violatedPolicy != null)
// //                    {
// //                        SimpleMailMessage smm = new SimpleMailMessage();
// //                        smm.setTo("anubhav.ranjan001@gmail.com");
// //                        smm.setSubject("Critical");
// //                        smm.setText("Some of the  policies have been Violated");
// //                        javaMailSender.send(smm);
// //                    }
//                 }
//                 System.out.println("Scanning another File");

//             }
//         }
//         System.out.println("Program Ended");
//     }
// }



public class newCon {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RiskRepository riskRepo;

    @Autowired
    private UserPolicyRepository policyRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserTestingLogsRepository userTestingLogsRepo;

    @Autowired
            private Risk_PolicyRepository risk_policyRepository;


    String violatedPolicy = "";
    String violatedRiks = "";



    String user_name="";

    @RequestMapping("/check")
    public String checkForRisk(@AuthenticationPrincipal UserDetails ud, Model model) throws IOException {

        user_name=ud.getUsername();
        checkRisk();
        List<UserTestingLogs> list=userTestingLogsRepo.findByUsername(user_name);
        model.addAttribute("getViolatedDetails",list);
        return "ShowResult";
    }

    @Scheduled(fixedDelay = 120000)
    public void checkRisk() throws IOException {
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CheckRegex cr = new CheckRegex();
        HashMap<String, Integer> map = new HashMap<>();


        System.out.println(System.getProperty("user.dir"));
        String dir=System.getProperty("user.dir")+"/uploads/"+user_name;

        //String dir = "/home/kuliza-568/Downloads/demo/uploads/" + user_name;


        //String dir = "/home/kuliza-269/Downloads/policy_module_demo/uploads/" + user_name + "/file.txt";


        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0)
        {
            for (int j = 0; j < listOfFiles.length; j++) {
                String str1 = "";
                violatedPolicy="";
                violatedRiks="";
                if (listOfFiles[j].isFile()) {

//                    long time =listOfFiles[j].lastModified();
//                    System.out.println(time);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    System.out.println("After Format : " + sdf.format(listOfFiles[j].lastModified()));


                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println(dtf.format(now));

                    boolean flag = findDifference(String.valueOf(sdf.format(listOfFiles[j].lastModified())), String.valueOf(dtf.format(now)));
                    System.out.println(flag);
                    if (flag == true) {

                        FileInputStream fis = new FileInputStream(listOfFiles[j]);
                        InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset());
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = br.readLine()) != null) {
                            str1 = str1 + line;
                        }
                        br.close();

                        System.out.println(".......");
                        System.out.println("Content of the testing docuement ");
                        List<String> words = Arrays.asList(str1.split(" "));
                        for (String s : words) System.out.println(s);
                        System.out.println(".......");

                        map.clear();
                        for (String s : words) {
                            map.put(s, map.getOrDefault(s, 0) + 1);
                        }
                        System.out.println(map);


                        List<UserPolicy> listPolicy = policyRepo.getEveryPolicy(user_name);

                        for (UserPolicy up1 : listPolicy) {
                            List<Risk_Policy> listRiskPolicy = risk_policyRepository.findByPolicyName(up1.getPolicy_name());
                            List<Long> riskId = new ArrayList<>();
                            for (Risk_Policy rp : listRiskPolicy) {
                                riskId.add(rp.getRisk_id());
                            }
//                        if (getRisk.length() == 0) continue;
//                        List<String> seperatedRisk = Arrays.asList(getRisk.split(","));
                            for (Long i : riskId) {
                                List<Risk_Details> getKeyWords = riskRepo.findAllKeyWords(i);
                                for (Risk_Details rd : getKeyWords) {
                                    int cnt1 = 0;
                                    String str = rd.getRisk_keyword();
                                    String strRegex = rd.getRisk_regex();
                                    List<String> riskKey = Arrays.asList(str.split(","));
                                    List<String> regexKey = Arrays.asList(strRegex.split(","));
                                    for (String s : riskKey) {
                                        if (s.length() == 0) continue;
                                        if (map.containsKey(s)) cnt1 += map.get(s);

                                    }
                                    for (String s : regexKey) {
                                        if (s.length() == 0) continue;
                                        cnt1 += cr.checkR(s, map);
                                    }


                                    if (cnt1 >= rd.getMatch_count()) {
                                        System.out.println("Risk With id " + rd.getRisk_id() + " exceed the match count thus policy with policy name " + up1.getPolicy_name() + " is violated");
                                        violatedPolicy = violatedPolicy + up1.getPolicy_name() + ",";
                                        violatedRiks = violatedRiks + rd.getRisk_id() + ",";
                                        UserTestingLogs utl = new UserTestingLogs();
                                        utl.setRisk_id(i + "");
                                        utl.setPolicy_name(up1.getPolicy_name());
                                        utl.setDate(java.time.LocalDate.now() + "");
                                        utl.setTime(java.time.LocalTime.now() + "");
                                        utl.setUser_name(user_name);
                                        userTestingLogsRepo.save(utl);
                                    } else
                                        System.out.println("No risk exceed the match count with risk id " + rd.getRisk_id());
                                }
                            }

                            System.out.println("Scanning for next policy...");
                            for (int i = 0; i < 4; i++) System.out.println(".");
                        }
                        System.out.println("No policies remaining");
//                    if (violatedPolicy != null)
//                    {
//                        SimpleMailMessage smm = new SimpleMailMessage();
//                        smm.setTo("anubhav.ranjan001@gmail.com");
//                        smm.setSubject("Critical");
//                        smm.setText("Some of the  policies have been Violated");
//                        javaMailSender.send(smm);
//                    }
                    }
                }
                    System.out.println("Scanning another File");


            }
        }
        System.out.println("Program Ended");
    }
    public boolean findDifference(String start_date,
                                  String end_date)
    {

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");
        boolean flag=true;


        try {

            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;


            if(difference_In_Days>0||difference_In_Hours>0||difference_In_Years>0||difference_In_Minutes>30)
            {
                flag=false;
            }
            System.out.print(
                    "Difference "
                            + "between two dates is: ");

            System.out.println(
                    difference_In_Years
                            + " years, "
                            + difference_In_Days
                            + " days, "
                            + difference_In_Hours
                            + " hours, "
                            + difference_In_Minutes
                            + " minutes, "
                            + difference_In_Seconds
                            + " seconds");
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

}



