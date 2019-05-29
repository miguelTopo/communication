package co.edu.udistrital.tts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class TtsFile {

	private static final Map<String, Integer> ttsMap = new HashMap<String, Integer>() {
		{
			put("", 1);
		}
	};

	public static List<Integer> getMapFileList(String text) {
		if (StringUtils.isEmpty(text))
			return Collections.emptyList();
		text = text.toLowerCase();
		String[] wordList = text.split(" ");
		if (wordList == null || wordList.length <= 0)
			return Collections.emptyList();

		List<Integer> mapList = new ArrayList<>(wordList.length);
		for (int i = 0; i < wordList.length; i++) {
			if (ttsMap.containsKey(wordList[i].trim()))
				mapList.add(ttsMap.get(wordList[i].trim()));
			else
				// aqui debemos mapear el correspondiente sonido a un silencio
				mapList.add(1);
		}
		return mapList;

	}

}
