/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
// @version:    $Id: Z3950Client.java,v 1.39 2003/05/09 12:54:43 rob_tice Exp $
// Copyright:   Copyright (C) 1999,2000 Knowledge Integration Ltd.
// @author:     Ian Ibbotson (ibbo@k-int.com)
// Company:     KI
// Description: 
//

//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2.1 of
// the license, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU general Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite
// 330, Boston, MA  02111-1307, USA.
// 

package cz.fi.muni.xkremser.editor.server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import com.google.inject.Inject;
import com.k_int.IR.IREvent;
import com.k_int.IR.IRQuery;
import com.k_int.IR.InformationFragment;
import com.k_int.IR.SearchException;
import com.k_int.IR.SearchTask;
import com.k_int.IR.Searchable;
import com.k_int.z3950.IRClient.Z3950Origin;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration.ServerConstants;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class Z3950Client.
 */
public class Z3950ClientImpl
        implements Z3950Client {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(Z3950ClientImpl.class);

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The Constant MZK_PROFILE_INDEX. */
    public static final int MZK_PROFILE_INDEX = 0;

    /** The Constant MZK_PROFILE_ID. */
    public static final String MZK_PROFILE_ID = "mzk";

    /** The Constant MUNI_PROFILE_INDEX. */
    public static final int MUNI_PROFILE_INDEX = 1;

    /** The Constant MUNI_PROFILE_ID. */
    public static final String MUNI_PROFILE_ID = "muni";

    /** The Constant NKP_SKC_PROFILE_INDEX. */
    public static final int NKP_SKC_PROFILE_INDEX = 2;

    /** The Constant NKP_SKC_PROFILE_ID. */
    public static final String NKP_SKC_PROFILE_ID = "nkp_skc";

    /** The Constant NKP_NKC_PROFILE_INDEX. */
    public static final int NKP_NKC_PROFILE_INDEX = 3;

    /** The Constant NKP_NKC_PROFILE_ID. */
    public static final String NKP_NKC_PROFILE_ID = "nkp_nkc";

    /** The Constant RECORD_SYNTAX. */
    public static final String RECORD_SYNTAX = "xml";

    /**
     * Instantiates a new z3950 client.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public Z3950ClientImpl(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Search.
     * 
     * @param field
     *        the field
     * @param what
     *        the what
     * @return the dublin core xml
     */
    @Override
    public List<DublinCore> search(Constants.SEARCH_FIELD field, String what) {

        // OIDRegister reg = OIDRegister.getRegister();

        String profile = null;
        String host = null;
        String port = null;
        String base = null;
        int barLength = 0;
        int profileIndex = -1;
        if ((profile = configuration.getZ3950Profile()) != null) {
            if (MZK_PROFILE_ID.equals(profile)) {
                profileIndex = MZK_PROFILE_INDEX;
            } else if (MUNI_PROFILE_ID.equals(profile)) {
                profileIndex = MUNI_PROFILE_INDEX;
            } else if (NKP_SKC_PROFILE_ID.equals(profile)) {
                profileIndex = NKP_SKC_PROFILE_INDEX;
            } else if (NKP_NKC_PROFILE_ID.equals(profile)) {
                profileIndex = NKP_NKC_PROFILE_INDEX;
            } else {
                LOGGER.warn("Invalid value (" + profile + ") for key "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE + " in editor configuration.");
            }
        }
        // host
        if ((host = configuration.getZ3950Host()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_HOST + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return null;
            } else {
                host = EditorConfiguration.ServerConstants.Z3950_DEFAULT_HOSTS[profileIndex];
            }
        }
        // port
        if ((port = configuration.getZ3950Port()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_PORT + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return null;
            } else {
                port = EditorConfiguration.ServerConstants.Z3950_DEFAULT_PORTS[profileIndex];
            }
        }
        // base
        if ((base = configuration.getZ3950Base()) == null) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_BASE + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return null;
            } else {
                base = EditorConfiguration.ServerConstants.Z3950_DEFAULT_BASES[profileIndex];
            }
        }
        // barcode length
        if ((barLength = configuration.getZ3950BarLength().intValue()) == ServerConstants.UNDEF.intValue()) {
            if (profileIndex == -1) {
                LOGGER.error("Neither " + EditorConfiguration.ServerConstants.Z3950_BAR_LENGTH + " nor "
                        + EditorConfiguration.ServerConstants.Z3950_PROFILE
                        + " is set in editor configuration!");
                return null;
            } else {
                barLength = EditorConfiguration.ServerConstants.Z3950_DEFAULT_BAR_LENGTH[profileIndex];
            }
        }

        Properties props = new Properties();
        props.put("ServiceHost", host);
        props.put("ServicePort", port);
        props.put("service_short_name", "meditor");
        props.put("service_long_name", "meditor");
        props.put("default_record_syntax", RECORD_SYNTAX);
        props.put("default_element_set_name", "F");

        // Remember you don't really need to do this...
        Observer fragment_count_observer = new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                IREvent e = (IREvent) arg;

                if (e.event_type == IREvent.SOURCE_RESET) {
                    System.err.println("TIME: Sub Fragment source reset");
                } else if (e.event_type == IREvent.FRAGMENT_COUNT_CHANGE) {
                    System.err.println("TIME: Number of fragments has changed to " + e.event_info);
                }
            }
        };

        // Observer[] all_observers = new Observer[] { fragment_count_observer };

        //        LOGGER.info("Z39.50: Connecting to " + host + " on port " + port);
        Searchable s = new Z3950Origin();
        s.init(props);

        IRQuery e = new IRQuery();
        e.collections.add(base);
        e.hints.put("record_syntax", RECORD_SYNTAX);
        String query = "@attrset bib-1 ";
        if (field != null) {
            switch (field) {
                case BAR:
                    query += "@attr 1=1099 "; // not tested
                    break;
                case SYSNO:
                    query += "@attr 1=12 ";
                    break;
                case TITLE:
                    query += "@attr 1=4 ";
                    break;
            }
        } else {
            if (barLength == what.length()) {
                query += "@attr 1=1099 "; // barcode
            } else {
                query += "@attr 1=12 "; // sysno
            }
        }
        e.setQueryModel(new com.k_int.IR.QueryModels.PrefixString(query + "\"" + what + "\""));
        LOGGER.debug("QUERY: " + e.getQueryModel().toString());
        List<DublinCore> returnList = new ArrayList<DublinCore>();
        try {
            SearchTask st = s.createTask(e, null, null/* all_observers */);
            int status = st.evaluate(150000);

            //            LOGGER.info("Private task status: " + st.lookupPrivateStatusCode(st.getPrivateTaskStatusCode()));
            Enumeration rs_enum = st.getTaskResultSet().elements();

            while (rs_enum.hasMoreElements()) {
                InformationFragment f = (InformationFragment) rs_enum.nextElement();
                returnList.add(DCUtils.getDC(f.toString()));
                //                LOGGER.info(f.toString());
            }

            st.destroyTask();
        } catch (SearchException se) {
            se.printStackTrace();
        } catch (com.k_int.IR.TimeoutExceededException tee) {
            tee.printStackTrace();
        }

        s.destroy();
        return returnList;
    }

    /**
     * The main method.
     * 
     * @param args
     *        the arguments
     */
    public static void main(String args[]) {

    }
}
