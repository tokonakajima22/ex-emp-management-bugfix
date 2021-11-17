package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@Size(min=1, max=20, message="名前は20文字以内にしてください")
	private String name;
	/** メールアドレス */
	@NotBlank(message="メールアドレスは必須です")
	@Email(message="Eメールの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@Pattern(regexp="^[0-9a-zA-Z]+$", message="英大文字、英小文字、数字で作成してください")
	@Size(min=8, message="パスワードは8文字以上にしてください")
	private String password;
	/** 確認用パスワード */
	private String confirmPassword;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}

	
}
