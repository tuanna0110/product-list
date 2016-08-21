package utils;

public class ConstantUtil {
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
	}
	
	public static enum RESULT_CODE {
		OK(0),
		ERROR(1);

		private int code;

		private RESULT_CODE(int code) {
			this.code = code;
		}

		public int getCode() {
			return this.code;
		}
	}
}
