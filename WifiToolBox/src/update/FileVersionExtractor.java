/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package update;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIOptions;

public class FileVersionExtractor {

    public interface Version extends Library {
        Version INSTANCE = Native.load("Version", Version.class, W32APIOptions.UNICODE_OPTIONS);

        int GetFileVersionInfoSizeW(String lptstrFilename, int dwDummy);

        boolean GetFileVersionInfoW(String lptstrFilename, int dwHandle, int dwLen, Pointer lpData);

        int VerQueryValueW(Pointer pBlock, String lpSubBlock, PointerByReference lplpBuffer, IntByReference puLen);
    }

    public static class VS_FIXEDFILEINFO extends com.sun.jna.Structure {
        public int dwSignature;
        public int dwStrucVersion;
        public int dwFileVersionMS;
        public int dwFileVersionLS;
        public int dwProductVersionMS;
        public int dwProductVersionLS;
        public int dwFileFlagsMask;
        public int dwFileFlags;
        public int dwFileOS;
        public int dwFileType;
        public int dwFileSubtype;
        public int dwFileDateMS;
        public int dwFileDateLS;

        @Override
        protected java.util.List<String> getFieldOrder() {
            return java.util.Arrays.asList(
                "dwSignature", "dwStrucVersion", "dwFileVersionMS", "dwFileVersionLS",
                "dwProductVersionMS", "dwProductVersionLS", "dwFileFlagsMask", "dwFileFlags",
                "dwFileOS", "dwFileType", "dwFileSubtype", "dwFileDateMS", "dwFileDateLS"
            );
        }

        public VS_FIXEDFILEINFO() {
            super();
        }

        public VS_FIXEDFILEINFO(Pointer p) {
            super(p);
        }
    }

    public static String getFileVersion(String filePath) {
        int dwDummy = 0;
        int versionLength = Version.INSTANCE.GetFileVersionInfoSizeW(filePath, dwDummy);
        if (versionLength <= 0) {
            throw new IllegalArgumentException("Invalid file or unable to retrieve version information.");
        }

        byte[] bufferArray = new byte[versionLength];
        Pointer lpData = new Memory(bufferArray.length);

        PointerByReference lplpBuffer = new PointerByReference();
        IntByReference puLen = new IntByReference();
        boolean fileInfoResult = Version.INSTANCE.GetFileVersionInfoW(filePath, 0, versionLength, lpData);
        if (!fileInfoResult) {
            throw new IllegalArgumentException("Invalid file or unable to retrieve version information.");
        }

        int verQueryVal = Version.INSTANCE.VerQueryValueW(lpData, "\\", lplpBuffer, puLen);
        if (verQueryVal <= 0) {
            throw new IllegalArgumentException("Invalid file or unable to retrieve version information.");
        }

        VS_FIXEDFILEINFO lplpBufStructure = new VS_FIXEDFILEINFO(lplpBuffer.getValue());
        lplpBufStructure.read();

        int majorVersion = (lplpBufStructure.dwFileVersionMS >> 16) & 0xFFFF;
        int minorVersion = (lplpBufStructure.dwFileVersionMS) & 0xFFFF;
        int buildNumber = (lplpBufStructure.dwFileVersionLS >> 16) & 0xFFFF;
        int revisionNumber = (lplpBufStructure.dwFileVersionLS) & 0xFFFF;

        return String.format("%d.%d.%d.%d", majorVersion, minorVersion, buildNumber, revisionNumber);
    }

}