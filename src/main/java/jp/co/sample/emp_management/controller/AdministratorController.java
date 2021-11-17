package jp.co.sample.emp_management.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.form.InsertAdministratorForm;
import jp.co.sample.emp_management.form.LoginForm;
import jp.co.sample.emp_management.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return toInsert();
		}
		Administrator administrator = new Administrator();
		try {
			if(form.getPassword().equals(form.getConfirmPassword())) {
				// フォームからドメインにプロパティ値をコピー
				BeanUtils.copyProperties(form, administrator);
				administratorService.insert(administrator);
//			redirectAttributes.addFlashAttribute("administrator", administrator);
				return "redirect:/";
			}else {
				System.out.println(form.getPassword() +" "+ form.getConfirmPassword());
				return toInsert();
			}
		}catch(Exception e){
			model.addAttribute("mailAddressError", "登録済のアドレスです");
		}
		return toInsert();
	}
	
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public Map<String, String> check(String password, String confirmPassword) {
		
		// 画面にレスポンスするデータをMapオブジェクトとして用意
		Map<String, String> map = new HashMap<>();

		// パスワード一致チェック
		String validationErrorMessage2 = null;
		if (password.equals(confirmPassword)) {
			validationErrorMessage2 = "確認用パスワード入力OK!";
		} else {
			validationErrorMessage2 = "パスワードが一致していません";
		}

		// レスポンスするMapオブジェクトに、メッセージを格納
		map.put("validationErrorMessage2", validationErrorMessage2);
		
		System.out.println(password + ":" + confirmPassword); // デバッグ用コンソール出力
		
		return map;
	}
	
	

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form
	 *            管理者情報用フォーム
	 * @param result
	 *            エラー情報格納用オブッジェクト
	 * @return ログイン後の従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult result, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
		if (administrator == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}else {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
}


