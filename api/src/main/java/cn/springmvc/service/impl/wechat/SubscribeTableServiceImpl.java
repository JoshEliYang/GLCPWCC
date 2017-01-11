package cn.springmvc.service.impl.wechat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.SubscribeTableDao;
import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeCountQuery;
import cn.springmvc.service.wechat.SubscribeTableService;

@Service
public class SubscribeTableServiceImpl implements SubscribeTableService {

	@Autowired
	private SubscribeTableDao subTableDao;

	Logger logger = Logger.getLogger(SubscribeTableServiceImpl.class);

	
	
	
	public List<List<SubscribeCount>> get(SubscribeCountQuery queryDat) throws Exception {
		//return subTableDao.query(queryDat);
		
		
		
        String start = queryDat.getStartDate();  
        String end = queryDat.getEndDate();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dBegin = sdf.parse(start);  
        Date dEnd = sdf.parse(end);  
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);  
		
		
        List<List<SubscribeCount>> qDats = new ArrayList<List<SubscribeCount>>();
        
		
        for(int i=0;i<listDate.size();i++){  
        	
        	List<SubscribeCount>  qDatList = new ArrayList<SubscribeCount>();
        	
        
        	qDatList =  subTableDao.queryDay(sdf.format(listDate.get(i)));

        	
        	
        	qDats.add(qDatList);
        	
        }
		
		
		return qDats;
	}
	
	
	 public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
	        List<Date> lDate = new ArrayList<Date>();  
	        lDate.add(beginDate);// 把开始时间加入集合  
	        Calendar cal = Calendar.getInstance();  
	        // 使用给定的 Date 设置此 Calendar 的时间  
	        cal.setTime(beginDate);  
	        boolean bContinue = true;  
	        while (bContinue) {  
	            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
	            cal.add(Calendar.DAY_OF_MONTH, 1);  
	            // 测试此日期是否在指定日期之后  
	            if (endDate.after(cal.getTime())) {  
	                lDate.add(cal.getTime());  
	            } else {  
	                break;  
	            }  
	        }  
	        lDate.add(endDate);// 把结束时间加入集合  
	        return lDate;  
	    }  
	
	
}
