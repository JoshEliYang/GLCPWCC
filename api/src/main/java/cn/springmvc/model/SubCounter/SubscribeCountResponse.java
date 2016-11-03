package cn.springmvc.model.SubCounter;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeCountResponse {
	Map<Integer, List<SubscribeCount>> result;

	/**
	 * 1.means year 2.means month 3.means day 4.means week
	 */
	int dateType;
	int basicId;

	@Override
	public String toString() {
		return "SubscibeCountResponse [result=" + result + ", dateType=" + dateType + ", basicId=" + basicId + "]";
	}

	public Map<Integer, List<SubscribeCount>> getResult() {
		return result;
	}

	public void setResult(Map<Integer, List<SubscribeCount>> result) {
		this.result = result;
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
