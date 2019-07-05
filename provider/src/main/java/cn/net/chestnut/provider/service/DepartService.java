package cn.net.chestnut.provider.service;

import cn.net.chestnut.provider.bean.Depart;

import java.util.List;

public interface DepartService {

    boolean saveDepart(Depart depart);

    boolean removeDepartById(int id);

    boolean modifyDepart(Depart depart);

    Depart getDepartById(int id);

    List<Depart> listAllDeparts();
}
