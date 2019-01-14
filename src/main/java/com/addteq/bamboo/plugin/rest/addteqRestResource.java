package com.addteq.bamboo.plugin.rest;

import java.util.StringTokenizer;

import com.atlassian.plugin.Plugin;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.upm.api.license.entity.PluginLicense;
import com.atlassian.upm.license.storage.lib.PluginLicenseStoragePluginUnresolvedException;
import com.atlassian.upm.license.storage.lib.ThirdPartyPluginLicenseStorageManager;
import com.atlassian.upm.osgi.Version;
import com.atlassian.upm.osgi.impl.Versions;
import com.google.common.base.Preconditions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
//http://localhost:6990/bamboo/rest/addteqrest/latest/check

//
/**
 * A resource of message.
 */
@Path("/check")
@AnonymousAllowed
public class addteqRestResource {

    private ThirdPartyPluginLicenseStorageManager licenseManager;
    private PluginAccessor pluginAccessor;

    public void setLicenseManager(ThirdPartyPluginLicenseStorageManager input) {
        this.licenseManager = input;
    }

    public void setPluginAccessor(PluginAccessor pluginAccessor) {
        this.pluginAccessor = ((PluginAccessor) Preconditions.checkNotNull(
                pluginAccessor, "pluginAccessor"));
    }

    @GET
    @AnonymousAllowed
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMessage() {
        if (checkUPMVersion()) {
            PluginLicense pluginLicense = null;

            String output = null;

            if (checkLicense()) {
                if (checkLicenseValidation()) {

                    try {
                        pluginLicense = licenseManager.getLicense().get();
                        String result = checklicenseType();
                        String key = pluginLicense.getPluginKey();
                        //String version = pluginAccessor.getPlugin(key).getPluginInformation().getVersion();
                        String version = "1.1";
                        if ("Commercial".equalsIgnoreCase(result)) {
                            output = result + "&" + "null" + "&" + version;
                        } else {

                            output = result + "&" + pluginLicense.getExpiryDate().get() + "&" + version;
                        }

                    } catch (PluginLicenseStoragePluginUnresolvedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    return Response.ok(new addteqRestResourceModel(output))
                            .build();
                } else {

                    String errormessage = null;
                    String version = "1.1";
                    try {
                        pluginLicense = licenseManager.getLicense().get();
                        errormessage = pluginLicense.getError().get().name();

                        if ("expired".equalsIgnoreCase(errormessage) || "version_mismatch".equalsIgnoreCase(errormessage) || "edition_mismatch".equalsIgnoreCase(errormessage)) {
                            String result = checklicenseType();
                            String key = pluginLicense.getPluginKey();
                            //String version = pluginAccessor.getPlugin(key).getPluginInformation().getVersion();
                            if ("Commercial".equalsIgnoreCase(result)) {
                                output = result + "&" + "null" + "&" + version;
                            } else {

                                output = result + "&" + pluginLicense.getExpiryDate().get() + "&" + version;
                            }

                        }else {
                            output = errormessage + "&" + "null" + "&" + version;
                        }
                    } catch (PluginLicenseStoragePluginUnresolvedException e) {
                        e.printStackTrace();
                    }
                    return Response.ok(new addteqRestResourceModel(output))
                            .build();
                }
            } else {
				//return Response.ok(new addteqRestResourceModel("130")).build();
                //String key = pluginLicense.getPluginKey();
                //String version = pluginAccessor.getPlugin(key).getPluginInformation().getVersion();
                output = "Unlicensed&" + "null&" + "1.1";

                return Response.ok(new addteqRestResourceModel(output))
                        .build();
            }
        } else {
            try {
                String output = null;
                for (PluginLicense license : this.licenseManager.getLicense()) {
                    if (license.getError().isDefined()) {
                        String errormessage = license.getError().get().name();
                        if ("expired".equalsIgnoreCase(errormessage)) {
                            String result = checklicenseType();
                            String key = license.getPluginKey();
                            //String version = pluginAccessor.getPlugin(key).getPluginInformation().getVersion();
                            String version = "1.1";
                            if ("Commercial".equalsIgnoreCase(result)) {
                                output = result + "&" + "null" + "&" + version;
                            } else {

                                output = result + "&" + license.getExpiryDate().get() + "&" + version;
                            }

                        }
                        return Response.ok(new addteqRestResourceModel(output))
                                .build();
                    }
                    return Response.ok(new addteqRestResourceModel("Unlicensed&" + "null&" + "1.1"))
                            .build();
                }

            } catch (PluginLicenseStoragePluginUnresolvedException e) {

            }
            return Response.ok(new addteqRestResourceModel("Unlicensed&" + "null&" + "1.1")).build();
        }
    }

    @AnonymousAllowed
    private boolean checkLicense() {
        try {
			//for (PluginLicense license : this.licenseManager.getLicense())
            //if (!license.getError().isDefined() && !license.getError().get().name().equalsIgnoreCase("expired"))
            //if (!license.getError().isDefined())
            if (licenseManager.getLicense().isDefined()) {
                return true;
            }
        } catch (PluginLicenseStoragePluginUnresolvedException e) {

        }
        return false;
    }

	// This function check for UPM Version since remote agents wasn't supported
    // until UPM 2.2.
    @AnonymousAllowed
    private boolean checkUPMVersion() {
        boolean result = false;
        Plugin plugin = pluginAccessor
                .getPlugin("com.atlassian.upm.atlassian-universal-plugin-manager-plugin");
        if (plugin == null) {
            result = false;
        } else {
            Version upmVersion = Versions.fromPlugin(plugin, false);
            String upmVersion_str = upmVersion.toString();
            StringTokenizer st = new StringTokenizer(upmVersion_str, ".");
            int first_index = Integer.parseInt((String) st.nextElement());
            if (first_index >= 2) {
                result = true;
            }
        }
        return result;
    }

    @AnonymousAllowed
    private boolean checkLicenseValidation() {
        boolean result = false;
        PluginLicense pluginLicense = null;
        try {
            pluginLicense = licenseManager.getLicense().get();
            if (pluginLicense.isValid()) {
                result = true;
            }

        } catch (PluginLicenseStoragePluginUnresolvedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @AnonymousAllowed
    private String checklicenseType() {

        String result = null;
        PluginLicense pluginLicense = null;
        try {
            pluginLicense = licenseManager.getLicense().get();
            if (pluginLicense.isEvaluation()) {
                result = "Evaluation";
            } else {
                result = "Commercial";
            }

        } catch (PluginLicenseStoragePluginUnresolvedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
