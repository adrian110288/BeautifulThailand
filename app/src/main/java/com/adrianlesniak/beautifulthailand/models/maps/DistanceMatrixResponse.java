package com.adrianlesniak.beautifulthailand.models.maps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 06/02/2017.
 */

public class DistanceMatrixResponse extends GoogleMapsResponse {

    @SerializedName("origin_addresses")
    public String[] originAddresses;

    @SerializedName("destination_addresses")
    public String[] destinationAddresses;

    public DistanceMatrixRow[] rows;

    public DistanceMatrixRow.DistanceMatrixElement getFirstElement() {

        if(rows.length > 0 && rows[0].elements.length > 0) {
            return rows[0].elements[0];
        }

        return null;
    }


}
