package com.honghao.cloud.userapi.test;

import com.honghao.cloud.userapi.dto.request.UpdateUserDTO;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenhonghao
 * @date 2019-10-30 14:39
 */
@Slf4j
public class Test02 {

    public static void main(String[] args) {

//        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
//        updateUserDTO.setDto("222");
//        updateUserDTO.setName("333333333");
//
//        packRabbitMq(updateUserDTO);
        StringBuilder stringBuilder = new StringBuilder();

        String result  = stringBuilder.append("11").append("22").append("33").append("44").toString();
        log.info(result);
//        System.out.println(JSON.toJSONString(updateUserDTO));
    }

    private static  <T> T  packRabbitMq(T data) {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setDto("111");
        getAndSetObject(updateUserDTO,data);
        return data;
    }
    private static void getAndSetObject(Object obj, Object obj2) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            try {
                if (f.get(obj)!=null && !"serialVersionUID".equals(f.getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(f.getName(), obj2.getClass());
                    Method wm = pd.getWriteMethod();
                    wm.invoke(obj2, f.get(obj));
                }
            } catch (IntrospectionException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    public <K extends UpdateUserDTO> K put(K key, UpdateUserDTO data) {
        return key;
    }


}
