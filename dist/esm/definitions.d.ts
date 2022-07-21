export interface SurepayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    printOnSurepay(options: {
        content: string;
        type: string;
    }): Promise<{
        content: string;
        type: string;
    }>;
    disableBluetoothListnerService(value: boolean): Promise<boolean>;
    getSurepayConnectionStatus(value: boolean): Promise<boolean>;
    getConnectedDeviceInfo(value: string): Promise<string>;
    enableBluetoothListenerService(value: string): Promise<string>;
    submitTransaction(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
    getLastTransactionDetails(value: string): Promise<string>;
    showLastTransactionDetails(value: string): Promise<string>;
    exportLastTransaction(value: boolean): Promise<boolean>;
    clearLastTransaction(value: boolean): Promise<boolean>;
    OnNewReceiptFromSurepayDevice(value: string): Promise<string>;
    OnConnectionLost(value: void): Promise<void>;
    OnConnectionEstablished(value: void): Promise<void>;
    OnError(value: string): Promise<string>;
}
export interface DeviceInfo {
    latitude: number;
    longitude: number;
}
