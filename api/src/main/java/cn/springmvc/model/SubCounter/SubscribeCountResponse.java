package cn.springmvc.model.SubCounter;

import java.util.Date;
import java.util.List;
import java.util.Map;

//import cn.springmvc.service.impl.manage.TagServiceImpl.TagDat;
import cn.springmvc.model.TagDat;
/**
 * 
 * @author johnson
 *
 */
public class SubscribeCountResponse {
	Map<Integer, List<SubscribeCount>> result;

	List<String> dates;
	List<SubscribeSetting> settingList;
	List<TagDat> tags;

	/**
	 * 1.means year 2.means month 3.means day 4.means week
	 */
	int dateType;
	int basicId;

	@Override
	public String toString() {
		return "SubscribeCountResponse [result=" + result + ", dates=" + dates + ", settingList=" + settingList
				+ ", tags=" + tags + ", dateType=" + dateType + ", basicId=" + basicId + "]";
	}

	public Map<Integer, List<SubscribeCount>> getResult() {
		return result;
	}

	public void setResult(Map<Integer, List<SubscribeCount>> result) {
		this.result = result;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public void setDates() {
		Date now = new Date();

	}

	public List<SubscribeSetting> getSettingList() {
		return settingList;
	}

	public void setSettingList(List<SubscribeSetting> settingList) {
		this.settingList = settingList;
	}

	public List<TagDat> getTags() {
		return tags;
	}

	public void setTags(List<TagDat> tags) {
		this.tags = tags;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

}
