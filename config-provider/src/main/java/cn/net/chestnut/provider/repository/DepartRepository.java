package cn.net.chestnut.provider.repository;

import cn.net.chestnut.provider.bean.Depart;
import org.springframework.data.jpa.repository.JpaRepository;

// 第一个泛型：当前Repository的操作对象类型
// 第二个泛型：当前Repository的操作对象的id类型
public interface DepartRepository extends JpaRepository<Depart, Integer> {
}
