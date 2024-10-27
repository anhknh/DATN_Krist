package com.example.kristp.utils;

import java.util.ArrayList;

public class Pagination {

    public ArrayList<Integer> getPage(int page,int totalPgae) {
        ArrayList<Integer> listPage = new ArrayList<>();
        if((totalPgae - 1) <= 0) {
            listPage.add(page);
            return listPage;
        }
        if(page == 0) {
            listPage.add(page);
            listPage.add(page + 1);
            return listPage;
        }

        if(page == totalPgae-1) {
            listPage.add(page-1);
            listPage.add(page);
            return listPage;
        }
        else {
            listPage.add(page - 1);
            listPage.add(page);
            listPage.add(page + 1);
            return listPage;
        }
    }
}
