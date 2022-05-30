export interface SurepayPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
