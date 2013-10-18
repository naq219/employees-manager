package haivu.qlnv.object;

public class MenuObject {
	String Loai;
	String So;

	public MenuObject(String loai, String so) {
		Loai = loai;
		So = so;
	}

	public String getLoai() {
		return Loai;
	}

	public void setLoai(String loai) {
		Loai = loai;
	}

	public String getSo() {
		return So;
	}

	public void setSo(String so) {
		So = so;
	}
}
