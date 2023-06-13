/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plugin.countryidd;
import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.json.JSONArray;
import org.json.JSONObject;

public final class CheckNumberAgainstIdd
{
    private CheckNumberAgainstIdd()
    {}

    @ScalarFunction("check_number_against_idd")
    @Description("Checks a telephone number against IDD (International Direct Dialing) codes.")
    @SqlType(StandardTypes.VARCHAR) // Adjust the return type accordingly
    public static String checkNumberAgainstIDD(@SqlType(StandardTypes.VARCHAR) String telephoneNo, @SqlType(StandardTypes.JSON) JSONArray jsonArr)
    {
        String telephoneNoString = new String(telephoneNo.getBytes());

        int i = telephoneNoString.length();

        while (i > 0) {
            String tempNo = telephoneNoString.substring(0, i);

            for (int j = 0; j < jsonArr.length(); j++) {
                JSONObject iddCountry = jsonArr.getJSONObject(j);
                if (tempNo.equals(iddCountry.get("rec_id").toString())) {
                    return iddCountry.getString("country");
                }
            }
            i--;
        }

        return "NA";
    }
}
