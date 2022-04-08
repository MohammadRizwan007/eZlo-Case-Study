package com.android.ezlo.Model;

import java.util.ArrayList;

public class Devices {


    ArrayList<DevicesDetails> Devices;

    public ArrayList<DevicesDetails> getDevices() {
        return Devices;
    }

    public static class DevicesDetails {
        String PK_Device;
        String MacAddress;
        String PK_DeviceType;
        String PK_DeviceSubType;
        String Firmware;
        String Server_Device;
        String Server_Event;
        String Server_Account;
        String InternalIP;
        String LastAliveReported;
        String Platform;

        public DevicesDetails(String Platform, String PK_Device, String MacAddress, String Firmware) {
            this.Platform = Platform;
            this.PK_Device = PK_Device;
            this.MacAddress = MacAddress;
            this.Firmware = Firmware;

        }


        public String getPK_Device() {
            return PK_Device;
        }

        public String getMacAddress() {
            return MacAddress;
        }

        public String getPK_DeviceType() {
            return PK_DeviceType;
        }

        public String getPK_DeviceSubType() {
            return PK_DeviceSubType;
        }

        public String getFirmware() {
            return Firmware;
        }

        public String getServer_Device() {
            return Server_Device;
        }

        public String getServer_Event() {
            return Server_Event;
        }

        public String getServer_Account() {
            return Server_Account;
        }

        public String getInternalIP() {
            return InternalIP;
        }

        public String getLastAliveReported() {
            return LastAliveReported;
        }

        public String getPlatform() {
            return Platform;
        }
    }

}
