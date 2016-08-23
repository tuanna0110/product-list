package services;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO: ホスティングサービスのオブジェクトストレージにイメージ保存するためのクラス
 * （現在ホスティングサービスがありません）
 */
public class ObjectStorageImage implements StoredImage {

	@Override
	public String saveImage(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getImage(String imageID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean deleteImage(String imageID) {
		// TODO Auto-generated method stub
		return false;
	}
}
