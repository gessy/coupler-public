package com.offgrid.coupler.core.model.converter;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Region;

import org.json.JSONObject;

import static com.offgrid.coupler.core.model.Constants.KEY_REGION_NAME;
import static com.offgrid.coupler.core.model.Constants.CHARACTER_ENCODING;

public class RegionMetadataConverter {

    public static byte[] convert(Region region) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEY_REGION_NAME, region.getName());
            String json = jsonObject.toString();
            return json.getBytes(CHARACTER_ENCODING);
        } catch (Exception e) {

        }

        return null;
    }

    public static String regionName(byte[] metadata) {
        try {
            String json = new String(metadata, CHARACTER_ENCODING);
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(KEY_REGION_NAME);
        } catch (Exception exception) {
        }

        return null;
    }

}
