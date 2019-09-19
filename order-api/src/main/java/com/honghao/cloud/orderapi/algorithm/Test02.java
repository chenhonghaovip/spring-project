package com.honghao.cloud.orderapi.algorithm;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * 克鲁斯卡尔最小树
 *
 * @author chenhonghao
 * @date 2019-09-19 10:27
 */
@Slf4j
public class Test02 {
    public static void main(String[] args) {
        Test02 test02 = new Test02();
        NodeDTO[] nodes = test02.initNodes();
        log.info("{}", JSON.toJSONString(nodes));
        Set<Integer> set = new HashSet<>();
        Set<NodeDTO> nodeDTOSet = new HashSet<>();
        int maxNode = 9;
        int maxSide = 8;
        int sum = 0;

        NodeDTO nodeDTO;
        for (NodeDTO node : nodes) {
            nodeDTO = node;
            if (set.contains(nodeDTO.getBegin()) && set.contains(nodeDTO.getEnd())) {
                continue;
            }
            for (NodeDTO dto : nodeDTOSet) {
            }
            set.add(nodeDTO.getBegin());
            set.add(nodeDTO.getEnd());
            sum += nodeDTO.getWeight();
        }
        log.info("sum的值为：{}",sum);




    }

    private NodeDTO[] initNodes(){
        int maxSide = 15;
        NodeDTO nodeDTO ;
        NodeDTO[] nodes = new NodeDTO[15];
        int begin = 0;
        int end = 0;
        int weight = 0;
        for (int i = 0;i<maxSide;i++){
            switch (i){
                case 0:
                    begin = 4;
                    end = 7;
                    weight = 7;
                    break;
                case 1:
                    begin = 2;
                    end = 8;
                    weight = 8;
                    break;
                case 2:
                    begin = 0;
                    end = 1;
                    weight = 10;
                    break;
                case 3:
                    begin = 0;
                    end = 5;
                    weight = 10;
                    break;
                case 4:
                    begin = 1;
                    end = 8;
                    weight = 12;
                    break;
                case 5:
                    begin = 3;
                    end = 7;
                    weight = 16;
                    break;
                case 6:
                    begin = 1;
                    end = 6;
                    weight = 16;
                    break;
                case 7:
                    begin = 5;
                    end = 6;
                    weight = 17;
                    break;
                case 8:
                    begin = 1;
                    end = 2;
                    weight = 18;
                    break;
                case 9:
                    begin = 6;
                    end = 7;
                    weight = 19;
                    break;
                case 10:
                    begin = 3;
                    end = 4;
                    weight = 20;
                    break;
                case 11:
                    begin = 3;
                    end = 8;
                    weight = 21;
                    break;
                case 12:
                    begin = 2;
                    end = 3;
                    weight = 22;
                    break;
                case 13:
                    begin = 3;
                    end = 6;
                    weight = 24;
                    break;
                case 14:
                    begin = 4;
                    end = 5;
                    weight = 26;
                    break;
                default:
                    break;
            }
            nodeDTO = NodeDTO.builder().begin(begin).end(end).weight(weight).build();
            nodes[i] = nodeDTO;
        }
        return nodes;
    }
}
