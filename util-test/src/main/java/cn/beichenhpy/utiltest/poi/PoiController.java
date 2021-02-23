package cn.beichenhpy.utiltest.poi;

import cn.beichenhpy.utiltest.poi.modal.DriverToSurface;
import cn.beichenhpy.utiltest.poi.modal.SectionToClassCtl;
import cn.beichenhpy.utiltest.poi.modal.SurfaceToWeight;
import cn.beichenhpy.utiltest.poi.modal.WeightInfo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO
 * @since 2021/2/23 14:39
 */
@RestController
public class PoiController {
    @RequestMapping("/export")
    public ModelAndView export() {
        ModelAndView modelAndView = new ModelAndView(new JeecgEntityExcelView());
        modelAndView.addObject(NormalExcelConstants.FILE_NAME, "测试");
        modelAndView.addObject(NormalExcelConstants.CLASS, DriverToSurface.class);
        modelAndView.addObject(NormalExcelConstants.PARAMS,new ExportParams("标题","我","表"));
        modelAndView.addObject(NormalExcelConstants.DATA_LIST, prepareDriver());
        return modelAndView;
    }


    public List<WeightInfo> prepareWeight() {
        List<WeightInfo> weightInfoList = new ArrayList<>();
        WeightInfo weightInfo = new WeightInfo();
        WeightInfo weightInfo1 = new WeightInfo();
        WeightInfo weightInfo2 = new WeightInfo();
        WeightInfo weightInfo3 = new WeightInfo();
        weightInfo.setWeight(20);
        weightInfo.setPrice(10.00);
        weightInfo.setCarTimes(4);
        weightInfo.setProduction(40);
        weightInfo.setTotalPrice(40.00);
        weightInfo1.setWeight(50);
        weightInfo1.setPrice(20.00);
        weightInfo1.setCarTimes(4);
        weightInfo1.setProduction(40);
        weightInfo1.setTotalPrice(80.00);
        weightInfo2.setWeight(50);
        weightInfo2.setPrice(20.00);
        weightInfo2.setCarTimes(5);
        weightInfo2.setProduction(40);
        weightInfo2.setTotalPrice(80.00);
        weightInfo3.setWeight(50);
        weightInfo3.setPrice(30.00);
        weightInfo3.setCarTimes(4);
        weightInfo3.setProduction(40);
        weightInfo3.setTotalPrice(120.00);
        weightInfoList.add(weightInfo);
        weightInfoList.add(weightInfo1);
        weightInfoList.add(weightInfo2);
        weightInfoList.add(weightInfo3);
        return weightInfoList;
    }

    public List<SurfaceToWeight> prepareSurface() {
        List<SurfaceToWeight> surfaceToWeightList = new ArrayList<>();
        SurfaceToWeight surfaceToWeight = new SurfaceToWeight();
        SurfaceToWeight surfaceToWeight1 = new SurfaceToWeight();
        surfaceToWeight.setId(1);
        surfaceToWeight.setSurface("工作面1");
        surfaceToWeight.setWeightInfoList(prepareWeight());
        surfaceToWeightList.add(surfaceToWeight);
        surfaceToWeight1.setId(2);
        surfaceToWeight1.setSurface("工作面2");
        surfaceToWeight1.setWeightInfoList(prepareWeight());
        surfaceToWeightList.add(surfaceToWeight);
        return surfaceToWeightList;
    }

    public List<DriverToSurface> prepareDriver(){
        List<DriverToSurface> driverToSurfaceList = new ArrayList<>();
        DriverToSurface driverToSurface = new DriverToSurface();
        driverToSurface.setId(1);
        driverToSurface.setDriver("测试人员");
        driverToSurface.setSurfaceToWeightList(prepareSurface());
        driverToSurfaceList.add(driverToSurface);
        return driverToSurfaceList;
    }

}
