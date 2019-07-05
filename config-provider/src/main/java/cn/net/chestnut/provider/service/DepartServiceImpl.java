package cn.net.chestnut.provider.service;

import cn.net.chestnut.provider.bean.Depart;
import cn.net.chestnut.provider.repository.DepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
public class DepartServiceImpl implements DepartService {
    @Autowired
    private DepartRepository repository;

    // 读取配置文件中的属性值
    @Value("${server.port}")
    private int port;

    @Value("${flag}")
    private String flag;

    @Override
    public boolean saveDepart(Depart depart) {
        Depart result = repository.save(depart);
        if(result != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean removeDepartById(int id) {
        // 对于deleteById()方法，若DB中不存在该id，而执行了其删除操作，将抛出异常
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        Depart result = repository.save(depart);
        if(result != null) {
            return true;
        }
        return false;
    }

    @Override
    public Depart getDepartById(int id) {
        if(repository.existsById(id)) {
            Depart depart = repository.getOne(id);
            // 部门名称加上flag
            depart.setName(flag + " " + depart.getName() + port);
            return depart;
        }
        Depart depart = new Depart();
        depart.setName(flag + " no this depart" + port);
        return depart;
    }

    @Override
    public List<Depart> listAllDeparts() {
        List<Depart> departs = repository.findAll();
        for(Depart depart : departs) {
            // 部门名称后加上端口号
            depart.setName(depart.getName() + port);
        }
        return departs;
    }
}
