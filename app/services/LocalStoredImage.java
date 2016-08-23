package services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import models.ImageIdCounter;
import play.Logger;

/**
 * 同じなサーバのファイルシステムにイメージを保存するためのクラス
 */
public class LocalStoredImage implements StoredImage {

	public final static String IMG_DIR = "/tmp/productlist_img/";

	@Override
	public String saveImage(InputStream is) throws IOException {
		/*
		 * データベースのAUTO_INCREMENT属性から唯一なイメージのIDを発生する これよりはるかに良い方法が必要だと思います。
		 */
		ImageIdCounter imageIdCounter = new ImageIdCounter();
		imageIdCounter.save();
		Files.copy(is, Paths.get(IMG_DIR + imageIdCounter.getId()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		return imageIdCounter.getId().toString();
	}

	@Override
	public InputStream getImage(String imageID) {
		try {
			File file = new File(IMG_DIR + imageID);
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			return is;
		} catch (FileNotFoundException e) {
			Logger.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean deleteImage(String imageID) {
		if (Files.exists(Paths.get(IMG_DIR + imageID))) {
			try {
				Files.delete(Paths.get(IMG_DIR + imageID));
			} catch (IOException e) {
				Logger.warn("ファイルを削除できません　id=" + imageID);
				return false;
			}
		}
		return true;
	}

}
