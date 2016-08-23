package utils;

public class ConstantUtil {
    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
	public static int FILE_MAX_SIZE = 2*1024*1024;
	public static enum IMAGE_TYPE_WHITELIST {
		PNG("image/png"),
		JPEG("image/jpeg");

		private String value;

		private IMAGE_TYPE_WHITELIST(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	};	
	
	public static enum ORDER_BY {
		ID(1) {
			@Override
			public String toString() {
				return "id";
			}
		},
		ID_DESC(-1) {
			@Override
			public String toString() {
				return "id desc";
			}
		},
		PRICE(2) {
			@Override
			public String toString() {
				return "price";
			}
		},
		PRICE_DESC(-2) {
			@Override
			public String toString() {
				return "price desc";
			}
		};

		private int value;

		private ORDER_BY(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	};
	
	public static enum ERROR_CODE {
		OK(0, "success"),
		SYSTEM_ERROR(1, "system.error"),
		LOGIN_FAILED(2, "login.failed"),
		PRODUCT_NOT_FOUND(10, "product.not.found"),
		INVALID_INPUT(11, "invalid.input"),
		FILE_TOO_LARGE(12, "file.too.large"),
		FILE_INVALID_FORMAT(15, "file.invalid.format"),
		FILE_CANNOT_DELETE(17, "file.cannot.delete");

		private int code;
		private String messageCode;

		private ERROR_CODE(int code, String messageCode) {
			this.code = code;
			this.messageCode = messageCode;
		}

		public int getCode() {
			return this.code;
		}
		
		public String getMessageCode() {
			return this.messageCode;
		}
	}
}
