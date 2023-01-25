import { WebPlugin } from '@capacitor/core';
import type { SurepayPlugin } from './definitions';
export declare class SurepayWeb extends WebPlugin implements SurepayPlugin {
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
    getBase64(options: {
        content: string;
    }): Promise<{
        content: string;
    }>;
    enableBluetoothListenerService(value: string): Promise<string>;
    disableBluetoothListnerService(value: boolean): Promise<boolean>;
    getSurepayConnectionStatus(value: boolean): Promise<boolean>;
    getConnectedDeviceInfo(value: string): Promise<string>;
    submitTransaction(options: {
        amount: string;
    }): Promise<{
        amount: string;
    }>;
    getLastTransactionDetails(value: string): Promise<string>;
    showLastTransactionDetails(value: string): Promise<string>;
    getPrinterStatus(value: string): Promise<string>;
    exportLastTransaction(value: boolean): Promise<boolean>;
    clearLastTransaction(value: boolean): Promise<boolean>;
    OnNewReceiptFromSurepayDevice(value: string): Promise<string>;
    OnConnectionLost(value: any): Promise<void>;
    OnConnectionEstablished(value: any): Promise<void>;
    OnError(value: string): Promise<string>;
}
