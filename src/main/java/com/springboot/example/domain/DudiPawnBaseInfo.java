package com.springboot.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("DUDI_PAWN_BASE_INFO")
public class DudiPawnBaseInfo {

    private String dudiPawnBaseInfoId;

    private String assetTypeOneNew;

    private String assetTypeTwoNew;

    private String assetTypeThree;

}
