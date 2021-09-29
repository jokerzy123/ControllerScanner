package com.fr.license.selector;

import com.fr.json.JSONObject;
import com.fr.plugin.context.PluginMarker;
import com.fr.regist.FunctionPoint;
import com.fr.regist.License;

/**
 * 伪造一个。
 *
 * @author Zhanying
 * @version 10.0
 * Created by Zhanying on 2021/9/29.
 */
public class LicenseContext {
    @Deprecated
    public static License currentLicense() {
        return new License() {
            @Override
            public int getInt(String s, int i) {
                return 0;
            }

            @Override
            public long getLong(String s, long l) {
                return 0;
            }

            @Override
            public boolean getBoolean(String s, boolean b) {
                return false;
            }

            @Override
            public String getString(String s, String s1) {
                return null;
            }

            @Override
            public boolean isOvertime() {
                return false;
            }

            @Override
            public boolean isOnTrial() {
                return false;
            }

            @Override
            public boolean isTemp() {
                return false;
            }

            @Override
            public long deadline() {
                return 0;
            }

            @Override
            public int maxConcurrencyLevel() {
                return 0;
            }

            @Override
            public int maxDataConnectionLevel() {
                return 0;
            }

            @Override
            public int maxReportletLevel() {
                return 0;
            }

            @Override
            public int maxDecisionUserLevel() {
                return 0;
            }

            @Override
            public int maxMobileUserLevel() {
                return 0;
            }

            @Override
            public String getVersion() {
                return null;
            }

            @Override
            public boolean isVersionMatch() {
                return false;
            }

            @Override
            public String getAppName() {
                return null;
            }

            @Override
            public boolean isAppNameMatch() {
                return false;
            }

            @Override
            public String getAppContent() {
                return null;
            }

            @Override
            public boolean isAppContentMatch() {
                return false;
            }

            @Override
            public String getDongleSerialNum() {
                return null;
            }

            @Override
            public boolean isDongleSerialNumMatch() {
                return false;
            }

            @Override
            public boolean isMultiServer() {
                return false;
            }

            @Override
            public String getUUID() {
                return null;
            }

            @Override
            public boolean isUUIDMatch() {
                return false;
            }

            @Override
            public String getMacAddress() {
                return null;
            }

            @Override
            public boolean isMacAddressMatch() {
                return false;
            }

            @Override
            public String templateEncryptionKey(String s) {
                return null;
            }

            @Override
            public String companyName(String s) {
                return null;
            }

            @Override
            public String projectName(String s) {
                return null;
            }

            @Override
            public boolean support(FunctionPoint functionPoint) {
                return false;
            }

            @Override
            public boolean support(PluginMarker pluginMarker) {
                return false;
            }

            @Override
            public JSONObject getJSONObject() {
                return null;
            }

            @Override
            public long signature() {
                return 0;
            }
        };
    }
}
