# rewaasurepay

Rewaa Surepay Android plugin

## Install

```bash
npm install rewaasurepay
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`disableBluetoothListnerService(...)`](#disablebluetoothlistnerservice)
* [`getSurepayConnectionStatus(...)`](#getsurepayconnectionstatus)
* [`getConnectedDeviceInfo(...)`](#getconnecteddeviceinfo)
* [`enableBluetoothListenerService(...)`](#enablebluetoothlistenerservice)
* [`submitTransaction(...)`](#submittransaction)
* [`getLastTransactionDetails(...)`](#getlasttransactiondetails)
* [`showLastTransactionDetails(...)`](#showlasttransactiondetails)
* [`exportLastTransaction(...)`](#exportlasttransaction)
* [`clearLastTransaction(...)`](#clearlasttransaction)
* [`OnNewReceiptFromSurepayDevice(...)`](#onnewreceiptfromsurepaydevice)
* [`OnConnectionLost(...)`](#onconnectionlost)
* [`OnConnectionEstablished(...)`](#onconnectionestablished)
* [`OnError(...)`](#onerror)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### disableBluetoothListnerService(...)

```typescript
disableBluetoothListnerService(value: boolean) => Promise<boolean>
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>boolean</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### getSurepayConnectionStatus(...)

```typescript
getSurepayConnectionStatus(value: boolean) => Promise<boolean>
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>boolean</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### getConnectedDeviceInfo(...)

```typescript
getConnectedDeviceInfo(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### enableBluetoothListenerService(...)

```typescript
enableBluetoothListenerService(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### submitTransaction(...)

```typescript
submitTransaction(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### getLastTransactionDetails(...)

```typescript
getLastTransactionDetails(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### showLastTransactionDetails(...)

```typescript
showLastTransactionDetails(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### exportLastTransaction(...)

```typescript
exportLastTransaction(value: boolean) => Promise<boolean>
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>boolean</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### clearLastTransaction(...)

```typescript
clearLastTransaction(value: boolean) => Promise<boolean>
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>boolean</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### OnNewReceiptFromSurepayDevice(...)

```typescript
OnNewReceiptFromSurepayDevice(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### OnConnectionLost(...)

```typescript
OnConnectionLost(value: void) => Promise<void>
```

| Param       | Type              |
| ----------- | ----------------- |
| **`value`** | <code>void</code> |

--------------------


### OnConnectionEstablished(...)

```typescript
OnConnectionEstablished(value: void) => Promise<void>
```

| Param       | Type              |
| ----------- | ----------------- |
| **`value`** | <code>void</code> |

--------------------


### OnError(...)

```typescript
OnError(value: string) => Promise<string>
```

| Param       | Type                |
| ----------- | ------------------- |
| **`value`** | <code>string</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------

</docgen-api>
