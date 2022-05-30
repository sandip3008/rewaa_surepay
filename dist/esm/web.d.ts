import { WebPlugin } from '@capacitor/core';
import type { SurepayPlugin } from './definitions';
export declare class SurepayWeb extends WebPlugin implements SurepayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
