package com.ominfo.hra_app.ui.notifications.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataItems {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableDetailList = new LinkedHashMap<String, List<String>>();

        // As we are populating List of fruits, vegetables and nuts, using them here
        // We can modify them as per our choice.
        // And also choice of fruits/vegetables/nuts can be changed


        List<String> headOffice = new ArrayList<String>();
        headOffice.add("Manager-1");
        headOffice.add("Manager-1");
        headOffice.add("Manager-1");
        expandableDetailList.put("Today", headOffice);

        List<String> mumbai = new ArrayList<String>();
        mumbai.add("Masjid (Umesh)");
        mumbai.add("Malad (Madan)");
        mumbai.add("Ulhasnager (Akash)");
        /*mumbai.add("Opera House (Rohit)");
        mumbai.add("Vasai (sameer)");
        mumbai.add("Dadar (Jugal)");
        mumbai.add("Dapoda bhiwandi (Om Prakash)");
        mumbai.add("Rehnal Bhiwandi (Santosh)");
        mumbai.add("Andheri (Narayan)");
        mumbai.add("Bhayander (Virabhai)");
        mumbai.add("Kalbadevi (Vipul)");
        mumbai.add("Vadgadi (Suhash)");*/
        expandableDetailList.put("Yesterday", mumbai);

        List<String> pune = new ArrayList<String>();
        pune.add("Pune-City (Anil)");
        expandableDetailList.put("Last Week", pune);

        List<String> gujrat = new ArrayList<String>();
        gujrat.add("Vapi (Jignesh)");
        gujrat.add("Ankleshwar (Shahnawaz)");
        gujrat.add("Kadodara (Jashwant)");
       /* gujrat.add("Navsari (Sanjay)");
        gujrat.add("Valsad (Mehul)");
        gujrat.add("Vansda (Mehul)");
        gujrat.add("Songadh (Banwari)");
        gujrat.add("Surat (Sagar)");
        gujrat.add("Bharuch (Vijay)");
        gujrat.add("Bardoli (Kamlesh)");
        gujrat.add("Billimora (Amir)");
        gujrat.add("Chikhli (Sukhabhai)");
        gujrat.add("Vyara (Paresh)");
        gujrat.add("Surat-SHR DRWJA (Jayesh)");*/
        expandableDetailList.put("Last Month", gujrat);

         /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
        //Do something


           /* }
        }, 10);*/


        // Fruits are grouped under Fruits Items. Similarly the rest two are under
        // Vegetable Items and Nuts Items respecitively.
        // i.e. expandableDetailList object is used to map the group header strings to
        // their respective children using an ArrayList of Strings.
        return expandableDetailList;
    }
}

