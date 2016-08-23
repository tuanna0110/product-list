package services;

import java.io.IOException;
import java.io.InputStream;

/**
 * イメージ保存するためのインタフェース
 */
public interface StoredImage {
	public String saveImage(InputStream is) throws IOException;
	public InputStream getImage(String imageID);
	public boolean deleteImage(String imageID);
}
