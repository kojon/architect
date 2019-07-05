package cn.net.chestnut.provider.service;

import cn.net.chestnut.provider.bean.Depart;
import cn.net.chestnut.provider.repository.DepartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepository repository;

    // 插入
    @Override
    public boolean saveDepart(Depart depart) {
        log.info("saveDepart{}",depart);
        // repository的save()方法需要注意：
        // 参数对象若id为null，则执行的是insert
        // 参数对象若具有id，则执行的是update
        Depart obj = repository.save(depart);
        if (obj != null) {
            return true;
        }
        return false;
    }

    // 根据id删除
    @Override
    public boolean removeDepartById(int id) {
        log.info("removeDepartById{}",id);
        // 若指定id的对象不存在，则deleteById()会抛出异常
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        log.info("modifyDepart{}",depart);
        // repository的save()方法需要注意：
        // 参数对象若id为null，则执行的是insert
        // 参数对象若具有id，则执行的是update
        Depart obj = repository.save(depart);
        if (obj != null) {
            return true;
        }
        return false;
    }

    @Override
    public Depart getDepartById(int id) {
        log.info("getDepartById{}",id);
        int time = 110;
        if (id < 3) {
            time = 50;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (repository.existsById(id)) {
            return repository.getOne(id);
        }
        Depart depart = new Depart();
        depart.setName("no this depart");
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        log.info("listAllDeparts");
        return repository.findAll();
    }
}
