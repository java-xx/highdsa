package pers.husen.highdsa.common.entity.po.shiro;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Desc 系统用户实体类
 *
 * @Author 何明胜
 *
 * @Created at 2018年3月29日 下午3:34:45
 * 
 * @Version 1.0.5
 */
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String userName;

	private String userPassword;

	private String userEmail;

	private String userPhone;

	private String userPwdSalt;

	private String userState;

	private List<SysRole> sysRoleList;

	private List<SysRolePermission> sysRolePermissionList;

	@Override
	public String toString() {
		return "SysUser [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userEmail=" + userEmail + ", userPhone=" + userPhone + ", userPwdSalt=" + userPwdSalt
				+ ", userState=" + userState + ", sysRoleList=" + sysRoleList + ", sysRolePermissionList=" + sysRolePermissionList + "]";
	}

	public SysUser(Long userId, String userName, String userPassword, String userEmail, String userPhone, String userPwdSalt, String userState) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userPwdSalt = userPwdSalt;
		this.userState = userState;
	}

	public SysUser() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword == null ? null : userPassword.trim();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getUserPwdSalt() {
		return userPwdSalt;
	}

	public void setUserPwdSalt(String userPwdSalt) {
		this.userPwdSalt = userPwdSalt == null ? null : userPwdSalt.trim();
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState == null ? null : userState.trim();
	}

	/**
	 * @return the sysRoleList
	 */
	public List<SysRole> getSysRoleList() {
		return sysRoleList;
	}

	/**
	 * @param sysRoleList
	 *            the sysRoleList to set
	 */
	public void setSysRoleList(List<SysRole> sysRoleList) {
		this.sysRoleList = sysRoleList;
	}

	/**
	 * @return the sysRolePermissionList
	 */
	public List<SysRolePermission> getSysRolePermissionList() {
		return sysRolePermissionList;
	}

	/**
	 * @param sysRolePermissionList
	 *            the sysRolePermissionList to set
	 */
	public void setSysRolePermissionList(List<SysRolePermission> sysRolePermissionList) {
		this.sysRolePermissionList = sysRolePermissionList;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof SysUser)) {
			return false;
		}
		SysUser user = (SysUser) object;

		return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(userPassword, user.userPassword) && Objects.equals(userEmail, user.userEmail)
				&& Objects.equals(userPhone, user.userPhone) && Objects.equals(userPwdSalt, user.userPwdSalt) && Objects.equals(userState, user.userState);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, userName, userPassword);
	}
}