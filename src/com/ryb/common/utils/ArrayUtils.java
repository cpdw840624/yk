package com.ryb.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.dw.base.utils.string.StringUtil;

public class ArrayUtils {
	
	public static Integer[] converArrStrToInt(String arrStr,String split){
		if(StringUtil.stringIsNull(arrStr)){
			return new Integer[]{};
		}
		String[] arrStrs=arrStr.split(split);
		List<Integer> result=new ArrayList<Integer>();
		for (String arr : arrStrs) {
			if(StringUtil.stringIsNull(arr)){
				continue;
			}
			result.add(Integer.valueOf(arr));
		}
		return result.toArray(new Integer[]{});
	}
	
	public static String[] converArrStrToStr(String arrStr,String split){
		if(StringUtil.stringIsNull(arrStr)){
			return new String[]{};
		}
		String[] arrStrs=arrStr.split(split);
		List<String> result=new ArrayList<String>();
		for (String arr : arrStrs) {
			if(StringUtil.stringIsNull(arr)){
				continue;
			}
			result.add(arr);
		}
		return result.toArray(new String[]{});
	}
	
	public static void main(String[] args) {
		Integer[] r=ArrayUtils.converArrStrToInt("1,2,3,4", ",");
		for (Integer integer : r) {
			System.out.println(integer);
		}
	}
}
