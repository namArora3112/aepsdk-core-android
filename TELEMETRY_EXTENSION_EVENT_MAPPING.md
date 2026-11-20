# Android SDK: extension -> every inventoried API -> EventHub payloads

**Summary:** 1382 customer-facing callable declarations/overloads, 190 event/behavior profiles and 344 payload templates across 22 official Android source repositories are indexed below.

## Document intent

This is an API-first reference for building and auditing the telemetry catalog: **extension -> class/API signature -> profile -> direct and reviewed conditional downstream events**. It includes local-only models, builders, callbacks and version getters rather than silently dropping APIs with no EventHub event.

The scope is the exact Android source snapshots recorded below, not every released SDK version, iOS parity, installed-binary compatibility, or proof of a successful network transaction. Snapshot-specific source limitations are retained in each family. Deployment rules, automatic SDK work and arbitrary customer listeners can produce additional events; they are not an unconditional finite public-API chain.

Public fields/constants are not callable signatures. Compiler-generated synthetics and SDK-internal/extension-author service infrastructure are excluded explicitly in the family sections. An inherited contract is not counted again unless the source explicitly redeclares it. UI infrastructure and legacy Offer are identified as such, not presented as interchangeable modern extensions.

## Coverage and navigation

| Family | Callables / overloads | Profiles | Payload templates |
| --- | ---: | ---: | ---: |
| [Mobile Core / Legacy Identity / Lifecycle / Signal](#family-core) | 102 | 38 | 36 |
| [Messaging / Assurance / LiveUpdates / Concierge / UI](#family-messaging-ui) | 951 | 43 | 115 |
| [Edge / Edge Identity / Consent / Edge Bridge](#family-edge-family) | 63 | 23 | 31 |
| [Analytics / Audience / Campaign / Campaign Classic / Target](#family-classic-family) | 77 | 45 | 43 |
| [Media / Edge Media / Optimize](#family-media-optimize) | 106 | 21 | 86 |
| [Places / Flags / Offer / UserProfile](#family-places-new) | 83 | 20 | 33 |

Inventory and profile IDs are **local to their family**. For example, `places-new/P01` and another family's similarly named ID are distinct. Each signature table references the exact reusable payload/no-event profiles that follow it; overloads do not disappear into a single API-name row.

Counts include model accessors/builders and 706 public UI theme/configuration entries; they are **not** a count of event-emitting facade methods. The Messaging/UI family separates its 267 supporting-infrastructure declarations from the eligible API total.

Core uses its checked-in public ABI baseline plus bundled-extension source declarations. Other families use public-source declaration inventories, with their reconciliation notes retained. The counts measure documented source declarations, not calls observed in an app or independently certified binary ABI.

### Repository snapshot index

| Official repository | Reviewed HEAD | Family |
| --- | --- | --- |
| `adobe/aepsdk-analytics-android` | `ac2798cf7dc88ba86740f2702c0a605419fe902e` | [classic-family](#family-classic-family) |
| `adobe/aepsdk-assurance-android` | `f9c827a806c0f7d961d38dbd5e12522a7ab0eb8d` | [messaging-ui](#family-messaging-ui) |
| `adobe/aepsdk-audience-android` | `4a98fba70984f4ba281c60a1c4a2541ffe1028b4` | [classic-family](#family-classic-family) |
| `adobe/aepsdk-campaign-android` | `30aaf4b93dae675608f8e34d9a6ed6d48bf420f3` | [classic-family](#family-classic-family) |
| `adobe/aepsdk-campaignclassic-android` | `f9cdf6250dc00c36c21fd1e61c8af0ad64ae654e` | [classic-family](#family-classic-family) |
| `adobe/aepsdk-concierge-android` | `13dbfa839e70533956351f890c0586350500c344` | [messaging-ui](#family-messaging-ui) |
| `adobe/aepsdk-core-android` | `3a2eb50e437742e9738222bcc7e9099c4af32481` | [core](#family-core) |
| `adobe/aepsdk-edge-android` | `b10baf2515eda20a6cb78b8c424b85c3b6947fb2` | [edge-family](#family-edge-family) |
| `adobe/aepsdk-edgebridge-android` | `155e4c7df1a2fa3d340e62fe60cf23eba995755f` | [edge-family](#family-edge-family) |
| `adobe/aepsdk-edgeconsent-android` | `6c384b2b9bd5f0dc7de2bf5eea6db7c4f2acfce4` | [edge-family](#family-edge-family) |
| `adobe/aepsdk-edgeidentity-android` | `f842ea5ae8db42d698eed619bebb027bb04084dc` | [edge-family](#family-edge-family) |
| `adobe/aepsdk-edgemedia-android` | `abe1677e93f850bcc88ef1c14bbfea759f1ce9d6` | [media-optimize](#family-media-optimize) |
| `adobe/aepsdk-flags-android` | `47be95ed4932fd14311acff13b2f1f05490ef45f` | [places-new](#family-places-new) |
| `adobe/aepsdk-liveupdates-android` | `33fc48c50d78d74fb8ef2989ca06579a6ef195ed` | [messaging-ui](#family-messaging-ui) |
| `adobe/aepsdk-media-android` | `421ea167840796935dc1cd69c503897c6b5de797` | [media-optimize](#family-media-optimize) |
| `adobe/aepsdk-messaging-android` | `7b7916d902460d4beb76c28c0ea82388e0eab5e1` | [messaging-ui](#family-messaging-ui) |
| `adobe/aepsdk-offer-android` | `4a1e53bae1ddf69b12e55aee7ada6eabc2e85d8b` | [places-new](#family-places-new) |
| `adobe/aepsdk-optimize-android` | `6418d61d62cea704b3067ef043244c992e2bdfa0` | [media-optimize](#family-media-optimize) |
| `adobe/aepsdk-places-android` | `f390d2fed3e393c87afef7bb358530eae4d2a2de` | [places-new](#family-places-new) |
| `adobe/aepsdk-target-android` | `7c0ca4781edb70635d67baece42225d0f8c0895a` | [classic-family](#family-classic-family) |
| `adobe/aepsdk-ui-android` | `6da207d9a2db484f16bf6490b0ff02362a66d7b2` | [messaging-ui](#family-messaging-ui) |
| `adobe/aepsdk-userprofile-android` | `306bd0f33c9aa937116cbd27068c1493ba5b9eab` | [places-new](#family-places-new) |

Archived `aepsdk-analyticsedge-android`, testing-only `aepsdk-testutils-android` and the local custom Telemetry POC are outside the official extension-source inventory. Legacy Core UserProfile is absent from the reviewed Core checkout; standalone UserProfile is included.

Source paths in the family sections identify the reviewed repository checkout. Supplemental paths written as `sources/...` refer to the inventory workspace, not to this Markdown file. Repository name plus reviewed HEAD is the portable snapshot identifier; snapshot-specific branch/source-drift notes remain in the corresponding section.

## Payload and correlation conventions

Payload blocks are **normalized illustrative EventHub records**, not literal runtime captures and not the uploaded `sdk.usage` envelope. They use the requested `eventName`, `eventType`, `eventSource` and `eventData` labels. Actual SDK `Event` getters are `getName()`, `getType()`, `getSource()` and `getEventData()`.

Angle-bracket placeholders stand for redacted/dynamic values or, where explicitly marked, caller-defined keys. Typed placeholders such as `<boolean>`/`<epoch-ms>` describe runtime values, not literal strings the SDK sends. Fixed SDK flags remain literal. Optional and variable key sets are explained beside the profile; null event data is not silently changed to an empty map. Raw eventData is a diagnostic reference, **not an instruction to export identifiers, tokens, configuration or user content in telemetry**.

The common envelope can be retained alongside any event profile:

```json
{
  "eventId": "<original-event-uuid>",
  "parentId": null,
  "responseId": null,
  "timestamp": 1795000000000,
  "eventName": "Configuration Update",
  "eventType": "com.adobe.eventType.configuration",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "config.update": {
      "<configuration-key>": "<redacted>"
    }
  }
}
```

The numeric timestamp above is illustrative epoch milliseconds. Preserve the original `Event.getUniqueIdentifier()`, `getParentID()`, `getResponseID()` and `getTimestamp()`; do not generate replacement IDs or use collection time as the event timestamp.

| Reviewed modern Core builder operation | parentId | responseId |
| --- | --- | --- |
| New builder with no explicit link | null | null |
| `chainToParentEvent(trigger)` | trigger UUID | unchanged (normally null) |
| `inResponseToEvent(request)` | request UUID | request UUID |

The reviewed SDK response builders create new Events with their own UUIDs and timestamps. A responseContent source does **not** by itself imply responseId exists: LifecycleStart is parent-only linked. Shared-state version association is not an event parent link. Family-specific link rules take precedence; the unvendored legacy Offer response-helper mutation remains explicitly unverified rather than borrowing modern semantics.

Evidence: Core `code/core/src/main/java/com/adobe/marketing/mobile/Event.java:83-94,152-164,195-255,290-299,307-377`.

## What the catalog can and cannot infer

A public API owner, the code publishing a child event and its eventType namespace are different things. For example, Messaging can publish an EDGE-type retrieval event without the app calling public `Edge.sendEvent`.

Family profile labels such as DIRECT, DOWNSTREAM or API_CALL describe the reviewed source role, not automatically a public app invocation. The original schema-1 POC contained 11 names; the 16-name example at the end is a historical proposal, not the current runtime asset.

**Runtime implementation update:** the Telemetry worktree now generates a schema-2/catalog-version-2 projection from this Markdown using `scripts/generate-event-name-catalog.py --mapping /path/to/TELEMETRY_EXTENSION_EVENT_MAPPING.md` (`--check` verifies drift). It extracts 193 literal name/type/source signatures, with reviewed operation associations: 72 API_CALL, 23 API_RESPONSE and 98 WILD_EVENT rules. Matching uses the exact case-sensitive event name plus case-insensitive type/source guards, matching Core normalization. No eventData is inspected or exported. Shared public signatures retain candidates rather than guessing; internal child requests, notifications and generic Edge responses stay wild. Three dynamic signatures are excluded instead of creating wildcards. The generator records the source digest in `scripts/event-name-catalog-provenance.json`. Schema-1 assets retain legacy matching semantics. This is operation observation, not proof of the original public caller, successful completion or causal ancestry.

`NO_EVENT`/`CALLBACK_ONLY` entries have no event-name mapping to invent. Dynamic dispatch APIs forward caller-defined signatures. Shared request names must remain candidate sets/WILD_EVENT rather than a guessed public API. Known ambiguities include `Analytics Track`, `Configuration Update`, `Configure with File Path`, `IdentityRequestIdentity` and shared proposition finalization.

The API_CALL/API_RESPONSE labels in a telemetry source-category policy are classifications, not proof of a public entry, successful operation or one-to-one call count. Root origin may be automatic SDK processing or a delegating public API. Part B explicit entry instrumentation is needed for unambiguous method identity where those paths share events; see [the Part B example](TELEMETRY_PART_B_EXAMPLE.md).

### Explicit evidence boundaries

Places and Target have source-backed **UNINDEXED** fallback evidence after bounded Scout recovery. The legacy Offer helper's final link mutation and remote release availability are not asserted. Edge Identity operational data and Edge use different event types in the paired snapshots; a working end-to-end operational-data transaction is not inferred. The family sections retain the exact attempted lookups and conditions.

## Extension-family reference

<a id="family-core"></a>

## Mobile Core, Legacy Identity, Lifecycle and Signal

The Core facade/model inventory is reconciled against `code/core/api/core.api`; the three bundled extension facades are inventoried from their source declarations. ABI signatures use erased generic types; overloads remain separate. `Lifecycle` and `Signal` expose only `extensionVersion()` in this snapshot: lifecycleStart/lifecyclePause belong to MobileCore, and rule-generated Signal activity is not a fictional public Signal tracking API.

### Core API inventory

| Inventory ID | Extension / public callable | Profile(s) | Declaration evidence |
| --- | --- | --- | --- |
| `core-api-001` | Core public models: `AdobeCallback.call(Object): void` | `core.callback` | `code/core/api/core.api:2` |
| `core-api-002` | Core public models: `AdobeCallbackWithError.fail(AdobeError): void` | `core.callback` | `code/core/api/core.api:6` |
| `core-api-003` | Core public models: `AdobeError.getErrorCode(): int` | `core.none` | `code/core/api/core.api:20` |
| `core-api-004` | Core public models: `AdobeError.getErrorName(): String` | `core.none` | `code/core/api/core.api:21` |
| `core-api-005` | Core public models: `Event.cloneWithEventData(Map): Event` | `core.none` | `code/core/api/core.api:25` |
| `core-api-006` | Core public models: `Event.getEventData(): Map` | `core.none` | `code/core/api/core.api:26` |
| `core-api-007` | Core public models: `Event.getMask(): String[]` | `core.none` | `code/core/api/core.api:27` |
| `core-api-008` | Core public models: `Event.getName(): String` | `core.none` | `code/core/api/core.api:28` |
| `core-api-009` | Core public models: `Event.getParentID(): String` | `core.none` | `code/core/api/core.api:29` |
| `core-api-010` | Core public models: `Event.getResponseID(): String` | `core.none` | `code/core/api/core.api:30` |
| `core-api-011` | Core public models: `Event.getSource(): String` | `core.none` | `code/core/api/core.api:31` |
| `core-api-012` | Core public models: `Event.getTimestamp(): long` | `core.none` | `code/core/api/core.api:32` |
| `core-api-013` | Core public models: `Event.getTimestampInSeconds(): long` | `core.none` | `code/core/api/core.api:33` |
| `core-api-014` | Core public models: `Event.getType(): String` | `core.none` | `code/core/api/core.api:34` |
| `core-api-015` | Core public models: `Event.getUniqueIdentifier(): String` | `core.none` | `code/core/api/core.api:35` |
| `core-api-016` | Core public models: `Event.toString(): String` | `core.none` | `code/core/api/core.api:36` |
| `core-api-017` | Core public models: `Event.Builder(String, String, String)` | `core.none` | `code/core/api/core.api:40` |
| `core-api-018` | Core public models: `Event.Builder(String, String, String, String[])` | `core.none` | `code/core/api/core.api:41` |
| `core-api-019` | Core public models: `Event.Builder.build(): Event` | `core.none` | `code/core/api/core.api:42` |
| `core-api-020` | Core public models: `Event.Builder.chainToParentEvent(Event): Event.Builder` | `core.none` | `code/core/api/core.api:43` |
| `core-api-021` | Core public models: `Event.Builder.inResponseToEvent(Event): Event.Builder` | `core.none` | `code/core/api/core.api:44` |
| `core-api-022` | Core public models: `Event.Builder.setEventData(Map): Event.Builder` | `core.none` | `code/core/api/core.api:45` |
| `core-api-023` | Core public models: `EventCoder.decode(String): Event` | `core.none` | `code/core/api/core.api:49` |
| `core-api-024` | Core public models: `EventCoder.encode(Event): String` | `core.none` | `code/core/api/core.api:50` |
| `core-api-025` | Core public models: `EventHistoryRequest(Map, long, long)` | `core.none` | `code/core/api/core.api:54` |
| `core-api-026` | Core public models: `EventHistoryRequest.getFromDate(): long` | `core.none` | `code/core/api/core.api:55` |
| `core-api-027` | Core public models: `EventHistoryRequest.getMaskAsDecimalHash(): long` | `core.none` | `code/core/api/core.api:56` |
| `core-api-028` | Core public models: `EventHistoryRequest.getToDate(): long` | `core.none` | `code/core/api/core.api:57` |
| `core-api-029` | Core public models: `EventHistoryResult(int, Long, Long)` | `core.none` | `code/core/api/core.api:65` |
| `core-api-030` | Core public models: `EventHistoryResultHandler.call(Object): void` | `core.callback` | `code/core/api/core.api:78` |
| `core-api-031` | Core public models: `InitOptions()` | `core.none` | `code/core/api/core.api:192` |
| `core-api-032` | Core public models: `InitOptions.configureWithAppID(String): InitOptions` | `core.none` | `code/core/api/core.api:193` |
| `core-api-033` | Core public models: `InitOptions.configureWithFileInAssets(String): InitOptions` | `core.none` | `code/core/api/core.api:194` |
| `core-api-034` | Core public models: `InitOptions.configureWithFileInPath(String): InitOptions` | `core.none` | `code/core/api/core.api:195` |
| `core-api-035` | Core public models: `InitOptions.getLifecycleAdditionalContextData(): Map` | `core.none` | `code/core/api/core.api:196` |
| `core-api-036` | Core public models: `InitOptions.getLifecycleAutomaticTrackingEnabled(): boolean` | `core.none` | `code/core/api/core.api:197` |
| `core-api-037` | Core public models: `InitOptions.setLifecycleAdditionalContextData(Map): void` | `core.none` | `code/core/api/core.api:198` |
| `core-api-038` | Core public models: `InitOptions.setLifecycleAutomaticTrackingEnabled(boolean): void` | `core.none` | `code/core/api/core.api:199` |
| `core-api-039` | Mobile Core: `MobileCore.addPlugins(IAepPlugin[]): void` | `core.none` | `code/core/api/core.api:219` |
| `core-api-040` | Mobile Core: `MobileCore.clearUpdatedConfiguration(): void` | `core.config-clear` | `code/core/api/core.api:220` |
| `core-api-041` | Mobile Core: `MobileCore.collectMessageInfo(Map): void` | `core.collect-data` | `code/core/api/core.api:221` |
| `core-api-042` | Mobile Core: `MobileCore.collectPii(Map): void` | `core.pii` | `code/core/api/core.api:222` |
| `core-api-043` | Mobile Core: `MobileCore.configureWithAppID(String): void` | `core.config-app` | `code/core/api/core.api:223` |
| `core-api-044` | Mobile Core: `MobileCore.configureWithFileInAssets(String): void` | `core.config-assets` | `code/core/api/core.api:224` |
| `core-api-045` | Mobile Core: `MobileCore.configureWithFileInPath(String): void` | `core.config-path` | `code/core/api/core.api:225` |
| `core-api-046` | Mobile Core: `MobileCore.dispatchEvent(Event): void` | `core.dynamic` | `code/core/api/core.api:226` |
| `core-api-047` | Mobile Core: `MobileCore.dispatchEventWithResponseCallback(Event, long, AdobeCallbackWithError): void` | `core.dynamic` | `code/core/api/core.api:227` |
| `core-api-048` | Mobile Core: `MobileCore.extensionVersion(): String` | `core.none` | `code/core/api/core.api:228` |
| `core-api-049` | Mobile Core: `MobileCore.getApplication(): Application` | `core.none` | `code/core/api/core.api:229` |
| `core-api-050` | Mobile Core: `MobileCore.getLargeIconResourceID(): int` | `core.none` | `code/core/api/core.api:230` |
| `core-api-051` | Mobile Core: `MobileCore.getLogLevel(): LoggingMode` | `core.none` | `code/core/api/core.api:231` |
| `core-api-052` | Mobile Core: `MobileCore.getPlugin(Class): IAepPlugin` | `core.none` | `code/core/api/core.api:232` |
| `core-api-053` | Mobile Core: `MobileCore.getPrivacyStatus(AdobeCallback): void` | `core.privacy-get` | `code/core/api/core.api:233` |
| `core-api-054` | Mobile Core: `MobileCore.getSdkIdentities(AdobeCallback): void` | `core.sdk-identities` | `code/core/api/core.api:234` |
| `core-api-055` | Mobile Core: `MobileCore.getSmallIconResourceID(): int` | `core.none` | `code/core/api/core.api:235` |
| `core-api-056` | Mobile Core: `MobileCore.initialize(Application, InitOptions, AdobeCallback): void` | `core.initialize` | `code/core/api/core.api:236` |
| `core-api-057` | Mobile Core: `MobileCore.initialize(Application, String): void` | `core.initialize` | `code/core/api/core.api:237` |
| `core-api-058` | Mobile Core: `MobileCore.initialize(Application, String, AdobeCallback): void` | `core.initialize` | `code/core/api/core.api:238` |
| `core-api-059` | Mobile Core: `MobileCore.lifecyclePause(): void` | `core.lifecycle-pause` | `code/core/api/core.api:239` |
| `core-api-060` | Mobile Core: `MobileCore.lifecycleStart(Map): void` | `core.lifecycle-start` | `code/core/api/core.api:240` |
| `core-api-061` | Mobile Core: `MobileCore.registerEventListener(String, String, AdobeCallback): void` | `core.setup` | `code/core/api/core.api:241` |
| `core-api-062` | Mobile Core: `MobileCore.registerExtensions(List, AdobeCallback): void` | `core.setup` | `code/core/api/core.api:242` |
| `core-api-063` | Mobile Core: `MobileCore.resetIdentities(): void` | `core.reset` | `code/core/api/core.api:243` |
| `core-api-064` | Mobile Core: `MobileCore.setAdvertisingIdentifier(String): void` | `core.advertising` | `code/core/api/core.api:244` |
| `core-api-065` | Mobile Core: `MobileCore.setApplication(Application): void` | `core.setup` | `code/core/api/core.api:245` |
| `core-api-066` | Mobile Core: `MobileCore.setLargeIconResourceID(int): void` | `core.none` | `code/core/api/core.api:246` |
| `core-api-067` | Mobile Core: `MobileCore.setLogLevel(LoggingMode): void` | `core.none` | `code/core/api/core.api:247` |
| `core-api-068` | Mobile Core: `MobileCore.setPrivacyStatus(MobilePrivacyStatus): void` | `core.privacy-set` | `code/core/api/core.api:248` |
| `core-api-069` | Mobile Core: `MobileCore.setPushIdentifier(String): void` | `core.push` | `code/core/api/core.api:249` |
| `core-api-070` | Mobile Core: `MobileCore.setSmallIconResourceID(int): void` | `core.none` | `code/core/api/core.api:250` |
| `core-api-071` | Mobile Core: `MobileCore.setWrapperType(WrapperType): void` | `core.none` | `code/core/api/core.api:251` |
| `core-api-072` | Mobile Core: `MobileCore.trackAction(String, Map): void` | `core.track-action` | `code/core/api/core.api:252` |
| `core-api-073` | Mobile Core: `MobileCore.trackState(String, Map): void` | `core.track-state` | `code/core/api/core.api:253` |
| `core-api-074` | Mobile Core: `MobileCore.updateConfiguration(Map): void` | `core.config-update` | `code/core/api/core.api:254` |
| `core-api-075` | Mobile Core: `MobileCore.updateProfileAttributes(ProfileAttributes): void` | `core.attributes` | `code/core/api/core.api:255` |
| `core-api-076` | Core public models: `MobilePrivacyStatus.fromString(String): MobilePrivacyStatus` | `core.none` | `code/core/api/core.api:262` |
| `core-api-077` | Core public models: `MobilePrivacyStatus.getValue(): String` | `core.none` | `code/core/api/core.api:263` |
| `core-api-078` | Core public models: `ProfileAttributes.getTimeZone(): TimeZone` | `core.none` | `code/core/api/core.api:269` |
| `core-api-079` | Core public models: `ProfileAttributes.Builder()` | `core.none` | `code/core/api/core.api:273` |
| `core-api-080` | Core public models: `ProfileAttributes.Builder.build(): ProfileAttributes` | `core.none` | `code/core/api/core.api:274` |
| `core-api-081` | Core public models: `ProfileAttributes.Builder.setTimeZone(TimeZone): ProfileAttributes.Builder` | `core.none` | `code/core/api/core.api:275` |
| `core-api-082` | Core public models: `VisitorID(String, String, String, VisitorID.AuthenticationState)` | `core.none` | `code/core/api/core.api:304` |
| `core-api-083` | Core public models: `VisitorID.equals(Object): boolean` | `core.none` | `code/core/api/core.api:305` |
| `core-api-084` | Core public models: `VisitorID.getAuthenticationState(): VisitorID.AuthenticationState` | `core.none` | `code/core/api/core.api:306` |
| `core-api-085` | Core public models: `VisitorID.getId(): String` | `core.none` | `code/core/api/core.api:307` |
| `core-api-086` | Core public models: `VisitorID.getIdOrigin(): String` | `core.none` | `code/core/api/core.api:308` |
| `core-api-087` | Core public models: `VisitorID.getIdType(): String` | `core.none` | `code/core/api/core.api:309` |
| `core-api-088` | Core public models: `VisitorID.hashCode(): int` | `core.none` | `code/core/api/core.api:310` |
| `core-api-089` | Core public models: `VisitorID.AuthenticationState.fromInteger(int): VisitorID.AuthenticationState` | `core.none` | `code/core/api/core.api:317` |
| `core-api-090` | Core public models: `VisitorID.AuthenticationState.getValue(): int` | `core.none` | `code/core/api/core.api:318` |
| `core-api-091` | Core public models: `WrapperType.fromString(String): WrapperType` | `core.none` | `code/core/api/core.api:330` |
| `core-api-092` | Core public models: `WrapperType.getFriendlyName(): String` | `core.none` | `code/core/api/core.api:331` |
| `core-api-093` | Core public models: `WrapperType.getWrapperTag(): String` | `core.none` | `code/core/api/core.api:332` |
| `core-api-094` | Legacy Identity: `Identity.extensionVersion(): String` | `core.none` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:43` |
| `core-api-095` | Legacy Identity: `Identity.syncIdentifiers(Map<String, String>, VisitorID.AuthenticationState): void` | `core.identity-sync` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:89` |
| `core-api-096` | Legacy Identity: `Identity.syncIdentifier(String, String, VisitorID.AuthenticationState): void` | `core.identity-sync` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:145` |
| `core-api-097` | Legacy Identity: `Identity.appendVisitorInfoForURL(String, AdobeCallback<String>): void` | `core.identity-append` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:192` |
| `core-api-098` | Legacy Identity: `Identity.getUrlVariables(AdobeCallback<String>): void` | `core.identity-url` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:246` |
| `core-api-099` | Legacy Identity: `Identity.getIdentifiers(AdobeCallback<List<VisitorID>>): void` | `core.identity-read` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:278` |
| `core-api-100` | Legacy Identity: `Identity.getExperienceCloudId(AdobeCallback<String>): void` | `core.identity-read` | `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:314` |
| `core-api-101` | Lifecycle: `Lifecycle.extensionVersion(): String` | `core.none` | `code/lifecycle/src/phone/java/com/adobe/marketing/mobile/Lifecycle.java:29` |
| `core-api-102` | Signal: `Signal.extensionVersion(): String` | `core.none` | `code/signal/src/phone/java/com/adobe/marketing/mobile/Signal.java:30` |

### Core event chains

- initialize -> selected configureWith* request -> conditional Configuration Response/shared-state activity; no separate initialize root.
- setPrivacyStatus -> updateConfiguration -> Configuration Update -> conditional Configuration Response/shared state.
- lifecycleStart -> Lifecycle Resume -> conditional LifecycleStart and/or Application Launch (Foreground); lifecyclePause -> Lifecycle Pause -> conditional Application Close (Background). Lifecycle responseContent is parent-only linked.
- trackAction / trackState -> Analytics Track; consumer extensions can create additional conditional activity, but that is not another public API invocation.
- Legacy Identity reads -> IdentityRequestIdentity -> one-time response. URL APIs use the same request name with different data and their own response names. Sync APIs share the request name and may update shared state/queue a network sync. A processed network sync can emit both an unlinked and a paired UPDATED_IDENTITY_RESPONSE, not one unique public API completion.

### Core payload profiles

#### `core.none` - NO_EVENT

Local getter/setter, version lookup, registry operation, model construction, serialization or comparison; no dedicated EventHub dispatch. Building an Event does not publish it. Event.cloneWithEventData preserves the original identifiers and timestamp; it does not dispatch the clone.

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:84`; `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:1031`; `code/core/src/main/java/com/adobe/marketing/mobile/Event.java:152`; `code/core/src/main/java/com/adobe/marketing/mobile/Event.java:290`; `code/core/src/main/java/com/adobe/marketing/mobile/EventCoder.java:42`; `code/core/src/main/java/com/adobe/marketing/mobile/InitOptions.kt:17`; `code/core/src/main/java/com/adobe/marketing/mobile/ProfileAttributes.java:45`; `code/core/src/main/java/com/adobe/marketing/mobile/VisitorID.java:34`; `code/core/src/main/java/com/adobe/marketing/mobile/MobilePrivacyStatus.java:47`; `code/core/src/main/java/com/adobe/marketing/mobile/WrapperType.java:30`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:43`; `code/lifecycle/src/phone/java/com/adobe/marketing/mobile/Lifecycle.java:29`; `code/signal/src/phone/java/com/adobe/marketing/mobile/Signal.java:30`.

#### `core.callback` - CALLBACK_ONLY

Callback/interface contract, not an event producer. A caller implementation may do arbitrary work; invoking this contract is not itself a fixed SDK event.

Evidence: `code/core/api/core.api:1`; `code/core/api/core.api:5`; `code/core/api/core.api:82`.

#### `core.setup` - SETUP

No unique API-entry EventHub marker. Application setup/extension registration/listener registration can enable subsequent lifecycle, configuration or extension-generated/shared-state activity; those events do not prove which public setup API was invoked. Listener callbacks are not new events.

Conditional downstream profiles: `core.shared-state`.

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:57`; `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:221`; `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCoreInitializer.kt:87`.

#### `core.initialize` - SETUP

All three initialize overloads delegate to the initializer. Depending on InitOptions, initialization calls configureWithAppID, configureWithFileInPath or configureWithFileInAssets; bundled configuration selects none of these. Automatic lifecycle observation and discovered-extension registration are conditional. Repeated initialize calls are ignored (callback still invoked). There is no distinct initialize request or response event.

Conditional downstream profiles: `core.config-app`, `core.config-assets`, `core.config-path`, `core.shared-state`, `core.lifecycle-start`, `core.lifecycle-pause`.

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:121`; `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCoreInitializer.kt:87`.

#### `core.dynamic` - DYNAMIC

dispatchEvent and dispatchEventWithResponseCallback forward the caller's Event. Name, type, source, data and existing identifiers are caller-defined, not a fixed MobileCore event signature. The response-callback overload registers a response listener; it does not manufacture a fixed response event.

```json
[
  {
    "eventName": "<caller Event.name>",
    "eventType": "<caller Event.type>",
    "eventSource": "<caller Event.source>",
    "eventData": {
      "<caller-defined-key>": "<caller-defined-value>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:262`; `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:286`.

#### `core.advertising` - API_CALL

Advertising identifier may be processed by registered identity extensions. Their shared-state/network consequences are conditional, not a second public API invocation.

Conditional downstream profiles: `core.shared-state`.

```json
[
  {
    "eventName": "Set Advertising Identifier",
    "eventType": "com.adobe.eventType.generic.identity",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "advertisingidentifier": "<advertising-id>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:356`.

#### `core.push` - API_CALL

Push identifier root; registered consumers may conditionally update identity state. This is not a Messaging API-entry marker.

Conditional downstream profiles: `core.shared-state`, `core.identity-push-analytics`.

```json
[
  {
    "eventName": "Set Push Identifier",
    "eventType": "com.adobe.eventType.generic.identity",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "pushidentifier": "<push-token>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:376`.

#### `core.attributes` - API_CALL

Null/empty ProfileAttributes produces no request. The timeZone key is included only when present; Edge Identity can conditionally consume it.

```json
[
  {
    "eventName": "Update Profile Attributes",
    "eventType": "com.adobe.eventType.profileAttribute",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "timeZone": "<IANA-timezone>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:408`.

#### `core.pii` - API_CALL

Null/empty input produces no event. Context data is caller supplied; do not export the raw values in telemetry.

```json
[
  {
    "eventName": "Collect PII",
    "eventType": "com.adobe.eventType.generic.pii",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "contextdata": {
        "<caller-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:448`.

#### `core.collect-data` - API_CALL

Null/empty input produces no event. The entire eventData map is caller supplied, not a fixed schema.

```json
[
  {
    "eventName": "Collect Data",
    "eventType": "com.adobe.eventType.generic.data",
    "eventSource": "com.adobe.eventSource.os",
    "eventData": {
      "<caller-key>": "<redacted>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:487`.

#### `core.config-app` - API_CALL

Null app ID produces no event. Configuration loading/processing can later emit configuration response/shared-state activity.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Configure with App ID",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.appId": "<app-id>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:562`.

#### `core.config-assets` - API_CALL

Null filename produces no event. The event name is shared with configureWithFileInPath; only the data key differs.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Configure with File Path",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.assetFile": "<asset-filename>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:602`.

#### `core.config-path` - API_CALL

Null path produces no event. The event name is shared with configureWithFileInAssets; name-only mapping cannot distinguish them.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Configure with File Path",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.filePath": "<file-path>"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:645`.

#### `core.config-update` - API_CALL

Null map produces no event. Configuration Update is shared with setPrivacyStatus and SDK-internal configuration updates; it cannot unambiguously identify a public entry point.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Configuration Update",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.update": {
        "<configuration-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:683`.

#### `core.config-clear` - API_CALL

Clears programmatic configuration overrides; downstream configuration processing is conditional.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Clear Updated Configuration",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.clearUpdates": true
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:714`.

#### `core.privacy-set` - API_CALL

Null status produces no event. Delegates to updateConfiguration; the same name/type/source can also be emitted by a direct updateConfiguration call with this key. optedin is an illustrative enum value (also optedout/optunknown), not a hard-coded SDK flag.

Conditional downstream profiles: `core.config-response`, `core.shared-state`.

```json
[
  {
    "eventName": "Configuration Update",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.update": {
        "global.privacy": "optedin"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:741`.

#### `core.privacy-get` - API_CALL

Null callback produces no event. Configuration Response is the downstream callback event; it is not unique to getPrivacyStatus.

Conditional downstream profiles: `core.config-response`.

```json
[
  {
    "eventName": "Privacy Status Request",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "config.getData": true
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:768`.

#### `core.sdk-identities` - API_CALL

Null callback produces no event. No eventData is set on this request (null, not a fabricated empty map).

Conditional downstream profiles: `core.sdk-identities-response`.

```json
[
  {
    "eventName": "Get SDK Identities",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.requestIdentity",
    "eventData": null
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:825`.

#### `core.reset` - API_CALL

Registered extensions decide how to reset their state; one generic reset request is not one independently invoked API per consumer.

Conditional downstream profiles: `core.shared-state`, `core.identity-network-response`.

```json
[
  {
    "eventName": "Reset Identities Request",
    "eventType": "com.adobe.eventType.generic.identity",
    "eventSource": "com.adobe.eventSource.requestReset",
    "eventData": null
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:870`.

#### `core.lifecycle-start` - API_CALL

additionalcontextdata is the supplied map (or null). Session conditions determine which downstream Lifecycle events are emitted.

Conditional downstream profiles: `core.lifecycle-v1`, `core.lifecycle-launch`, `core.shared-state`.

```json
[
  {
    "eventName": "Lifecycle Resume",
    "eventType": "com.adobe.eventType.generic.lifecycle",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "action": "start",
      "additionalcontextdata": {
        "<caller-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:900`.

#### `core.lifecycle-pause` - API_CALL

Lifecycle processing may produce an application-close event; a pause call alone is not proof of upload success.

Conditional downstream profiles: `core.lifecycle-close`.

```json
[
  {
    "eventName": "Lifecycle Pause",
    "eventType": "com.adobe.eventType.generic.lifecycle",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "action": "pause"
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:934`.

#### `core.track-action` - API_CALL

Null action becomes an empty string; null context data becomes an empty map. Same event name/type/source as trackState. Registered Analytics/Edge Bridge/rules consumers may create conditional downstream activity.

```json
[
  {
    "eventName": "Analytics Track",
    "eventType": "com.adobe.eventType.generic.track",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "action": "<action-or-empty>",
      "contextdata": {
        "<caller-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:966`.

#### `core.track-state` - API_CALL

Null state becomes an empty string; null context data becomes an empty map. Same event name/type/source as trackAction; data contains state rather than action.

```json
[
  {
    "eventName": "Analytics Track",
    "eventType": "com.adobe.eventType.generic.track",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "state": "<state-or-empty>",
      "contextdata": {
        "<caller-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:999`.

#### `core.config-response` - DOWNSTREAM

Configuration producer publishes the effective configuration, not a fixed response body. Configuration Response may also occur without a public API call. If a trigger is supplied, inResponseToEvent sets both parentId and responseId to that trigger's ID; otherwise no such link is set.

```json
[
  {
    "eventName": "Configuration Response",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.responseContent",
    "eventData": {
      "<effective-configuration-key>": "<redacted>"
    }
  }
]
```

Evidence: `code/core/src/main/java/com/adobe/marketing/mobile/internal/configuration/ConfigurationExtension.kt:394`; `code/core/src/main/java/com/adobe/marketing/mobile/internal/configuration/ConfigurationExtension.kt:497`.

#### `core.sdk-identities-response` - API_RESPONSE

config.allIdentifiers is a JSON-encoded String, not an embedded object. inResponseToEvent links both parentId and responseId to Get SDK Identities.

```json
[
  {
    "eventName": "Configuration Response Identity",
    "eventType": "com.adobe.eventType.configuration",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "config.allIdentifiers": "<JSON-encoded-identities-string>"
    }
  }
]
```

Evidence: `code/core/src/main/java/com/adobe/marketing/mobile/internal/configuration/ConfigurationExtension.kt:412`.

#### `core.shared-state` - METADATA

EventHub metadata, not a public API entry or API-completion marker. State owner is the extension name; shared-state contents are not copied into this event. These builders set no parent/response link.

```json
[
  {
    "eventName": "Shared state change",
    "eventType": "com.adobe.eventType.hub",
    "eventSource": "com.adobe.eventSource.sharedState",
    "eventData": {
      "stateowner": "<extension-name>"
    }
  },
  {
    "eventName": "Shared state change (XDM)",
    "eventType": "com.adobe.eventType.hub",
    "eventSource": "com.adobe.eventSource.sharedState",
    "eventData": {
      "stateowner": "<extension-name>"
    }
  }
]
```

Evidence: `code/core/src/main/java/com/adobe/marketing/mobile/internal/eventhub/EventHub.kt:859`; `code/core/src/main/java/com/adobe/marketing/mobile/internal/eventhub/EventHubConstants.kt:20`.

#### `core.lifecycle-v1` - DOWNSTREAM

Conditional new-session result of Lifecycle processing. All numeric values shown are illustrative; maxsessionlength is the fixed seven-day limit (604800 seconds). chainToParentEvent sets parentId only, not responseId, despite the responseContent source.

```json
[
  {
    "eventName": "LifecycleStart",
    "eventType": "com.adobe.eventType.lifecycle",
    "eventSource": "com.adobe.eventSource.responseContent",
    "eventData": {
      "lifecyclecontextdata": {
        "<SDK-session-context-key>": "<redacted>"
      },
      "sessionevent": "start",
      "maxsessionlength": 604800,
      "starttimestampmillis": 1795000000000,
      "previoussessionstarttimestampmillis": 1794990000000,
      "previoussessionpausetimestampmillis": 1794991000000
    }
  }
]
```

Evidence: `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleV1Extension.java:187`; `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleConstants.java:21`; `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleConstants.java:100`.

#### `core.lifecycle-launch` - DOWNSTREAM

Only emitted with nonempty launch XDM. xdm is the SDK-generated launch map; data is optional caller free-form context. Parent link only.

```json
[
  {
    "eventName": "Application Launch (Foreground)",
    "eventType": "com.adobe.eventType.lifecycle",
    "eventSource": "com.adobe.eventSource.applicationLaunch",
    "eventData": {
      "xdm": {
        "<SDK-launch-XDM-key>": "<sanitized-value>"
      },
      "data": {
        "<caller-context-key>": "<redacted>"
      }
    }
  }
]
```

Evidence: `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleV2Extension.java:226`; `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleV2Constants.java:27`.

#### `core.lifecycle-close` - DOWNSTREAM

Only emitted with nonempty close XDM. Parent link only.

```json
[
  {
    "eventName": "Application Close (Background)",
    "eventType": "com.adobe.eventType.lifecycle",
    "eventSource": "com.adobe.eventSource.applicationClose",
    "eventData": {
      "xdm": {
        "<SDK-close-XDM-key>": "<sanitized-value>"
      }
    }
  }
]
```

Evidence: `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleV2Extension.java:260`; `code/lifecycle/src/main/java/com/adobe/marketing/mobile/lifecycle/LifecycleV2Constants.java:27`.

#### `core.identity-push-analytics` - DOWNSTREAM

Legacy Identity conditionally publishes an internal Analytics request when push opt-in state must be reported. Unchanged tokens/disabled paths can suppress it. a.push.optin is a String (true or false); the example uses true. This builder sets neither parentId nor responseId. It is not a public Analytics API invocation.

```json
[
  {
    "eventName": "AnalyticsForIdentityRequest",
    "eventType": "com.adobe.eventType.analytics",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "action": "Push",
      "contextdata": {
        "a.push.optin": "true"
      },
      "trackinternal": true
    }
  }
]
```

Evidence: `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1075`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1682`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityConstants.java:24`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityConstants.java:220`.

#### `core.identity-read` - API_CALL

getIdentifiers/getExperienceCloudId share an identical request with no data. Null callback prevents dispatch. All six behavioral Legacy Identity APIs share the name IdentityRequestIdentity; name-only attribution is ambiguous.

Conditional downstream profiles: `core.identity-response`.

```json
[
  {
    "eventName": "IdentityRequestIdentity",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.requestIdentity",
    "eventData": null
  }
]
```

Evidence: `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:278`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:382`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:729`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:818`.

#### `core.identity-url` - API_CALL

Null callback prevents dispatch; URL-variable request uses this flag. All six behavioral Legacy Identity APIs share the name IdentityRequestIdentity; name-only attribution is ambiguous.

Conditional downstream profiles: `core.identity-url-response`.

```json
[
  {
    "eventName": "IdentityRequestIdentity",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.requestIdentity",
    "eventData": {
      "urlvariables": true
    }
  }
]
```

Evidence: `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:246`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:382`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:729`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:818`.

#### `core.identity-append` - API_CALL

Null callback prevents dispatch. The returned URL may equal the input, including empty-input handling; do not infer successful identity enrichment. All six behavioral Legacy Identity APIs share the name IdentityRequestIdentity; name-only attribution is ambiguous.

Conditional downstream profiles: `core.identity-append-response`.

```json
[
  {
    "eventName": "IdentityRequestIdentity",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.requestIdentity",
    "eventData": {
      "baseurl": "<caller-url>"
    }
  }
]
```

Evidence: `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:192`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:382`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:729`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:818`.

#### `core.identity-sync` - API_CALL

syncIdentifier delegates to syncIdentifiers. Input validation can suppress dispatch. authenticationstate 0 is an illustrative UNKNOWN value; callers may choose another AuthenticationState. Processing is gated by configuration/privacy and may queue a network sync or skip it when current identifiers are already up-to-date. There is no synchronous public completion event. All six behavioral Legacy Identity APIs share the name IdentityRequestIdentity; name-only attribution is ambiguous.

Conditional downstream profiles: `core.shared-state`, `core.identity-network-response`.

```json
[
  {
    "eventName": "IdentityRequestIdentity",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.requestIdentity",
    "eventData": {
      "visitoridentifiers": {
        "<id-type>": "<redacted-id>"
      },
      "authenticationstate": 0,
      "forcesync": false,
      "issyncevent": true
    }
  }
]
```

Evidence: `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:89`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:382`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:729`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:818`.

#### `core.identity-response` - API_RESPONSE

Conditional Legacy Identity one-time read response. The state keys shown are conditional except lastsync; values and visitor-ID entries vary with stored state. inResponseToEvent links both IDs to the read request.

```json
[
  {
    "eventName": "IDENTITY_RESPONSE_CONTENT_ONE_TIME",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "mid": "<redacted-ecid>",
      "advertisingidentifier": "<redacted-ad-id>",
      "pushidentifier": "<redacted-push-token>",
      "blob": "<redacted>",
      "locationhint": "<redacted>",
      "visitoridslist": [
        {
          "<SDK-VisitorID-key>": "<redacted>"
        }
      ],
      "lastsync": 1795000000
    }
  }
]
```

Evidence: `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:729`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1324`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1764`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityConstants.java:137`.

#### `core.identity-url-response` - API_RESPONSE

Conditional response to getUrlVariables; both IDs reference its request. The URL-variable string is identity-bearing data and is redacted.

```json
[
  {
    "eventName": "IDENTITY_URL_VARIABLES",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "urlvariables": "<redacted-url-variables>"
    }
  }
]
```

Evidence: `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1056`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1764`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:395`.

#### `core.identity-append-response` - API_RESPONSE

Conditional response to appendVisitorInfoForURL; both IDs reference its request. This also covers unchanged/empty URL responses.

```json
[
  {
    "eventName": "IDENTITY_APPENDED_URL",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "updatedurl": "<redacted-returned-url>"
    }
  }
]
```

Evidence: `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1163`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1764`; `code/identity/src/phone/java/com/adobe/marketing/mobile/Identity.java:391`.

#### `core.identity-network-response` - CONDITIONAL_DOWNSTREAM

A queued Legacy Identity sync reaching HTTP 200 with a parsed response calls networkResponseLoaded. It emits an unlinked UPDATED_IDENTITY_RESPONSE broadcast and, when a trigger exists, a separate paired event with the same name/type/source/data. Each has a fresh UUID and build timestamp. State fields other than lastsync are conditional; updatesharedstate=true is present only when a shared-state update is required. The listener then conditionally creates shared state. Sync can be skipped, retried or rejected by configuration/privacy; network response processing checks current privacy again. These events also occur for non-public-API identity work, and do not prove a successful identity change or identify which public API was entered.

Conditional downstream profiles: `core.shared-state`.

```json
[
  {
    "eventName": "UPDATED_IDENTITY_RESPONSE",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "mid": "<redacted-ecid>",
      "advertisingidentifier": "<redacted-ad-id>",
      "pushidentifier": "<redacted-push-token>",
      "blob": "<redacted>",
      "locationhint": "<redacted>",
      "visitoridslist": [
        {
          "<SDK-VisitorID-key>": "<redacted>"
        }
      ],
      "lastsync": 1795000000,
      "updatesharedstate": true
    },
    "links": {
      "parentId": null,
      "responseId": null
    }
  },
  {
    "eventName": "UPDATED_IDENTITY_RESPONSE",
    "eventType": "com.adobe.eventType.identity",
    "eventSource": "com.adobe.eventSource.responseIdentity",
    "eventData": {
      "mid": "<redacted-ecid>",
      "advertisingidentifier": "<redacted-ad-id>",
      "pushidentifier": "<redacted-push-token>",
      "blob": "<redacted>",
      "locationhint": "<redacted>",
      "visitoridslist": [
        {
          "<SDK-VisitorID-key>": "<redacted>"
        }
      ],
      "lastsync": 1795000000,
      "updatesharedstate": true
    },
    "links": {
      "parentId": "<trigger-event-id>",
      "responseId": "<trigger-event-id>"
    }
  }
]
```

Evidence: `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1591`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:450`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1324`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityExtension.java:1764`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityHitsProcessing.java:46`; `code/identity/src/main/java/com/adobe/marketing/mobile/identity/IdentityConstants.java:215`.

### Core catalog attribution and scope

Name-only ambiguities include Analytics Track (trackAction/trackState), Configuration Update (updateConfiguration/setPrivacyStatus/internal callers), Configure with File Path (assets/path APIs), and IdentityRequestIdentity (six Legacy Identity APIs). Even a unique public-facade name can be reached through initialize or another SDK helper; use Part B instrumentation for provable public-entry identity. Do not copy raw identifier/configuration maps from these diagnostic examples into telemetry.

- Extension/ExtensionApi/ExtensionHelper/ExtensionDiscoveryService,ExtensionEventListener and SharedState* are extension-author infrastructure.
- Service-provider, rules-engine, plugin-implementation and internal packages are not customer extension API surfaces.
- Compiler-generated values/valueOf, synthetic bridges, data-class components/copy/equals/hashCode/toString are excluded. Explicitly authored VisitorID.equals/hashCode and Event.toString are included.
- InitOptions.Companion factories are JVM aliases of the three inventoried InitOptions factory APIs, not additional source APIs.
- Fields/constants (including EventType, EventSource, LoggingMode and EventHistoryResult fields) are not callable signatures.
- Core facade/model signatures use public ABI parameter erasures. Generic type arguments and callback payload types are not additional overloads.

---

<a id="family-messaging-ui"></a>

## Messaging, Assurance, Live Updates, Concierge and shared UI

Summary: Source-backed customer API inventory, separately classified supporting infrastructure, and normalized EventHub payload profiles for the five assigned Android repositories.

### Request flows

There are no HTTP routes for these Android facades. The customer application, FCM service, UI observer or registered Core plugin is the entry point.

* `Messaging.refreshInAppMessages` → coalescing `IamRefreshHandler` → **MSG_REFRESH** → Messaging listener → **MSG_FETCH** → Edge personalization processing → conditional **MSG_NOTIFICATION**. Update overloads enter through **MSG_UPDATE**, share the fetch producer and are separately network-availability gated.
* `Messaging.getPropositionsForSurfaces` → **MSG_GET** → cached CBE/inbox/content-card filtering → correlated response → `Map<Surface,List<Proposition>>` callback. This is not a fetch-from-network API.
* FCM → `MessagingService.handleRemoteMessage` → AJO selector → standard notification rendering/posting → self-init → **MSG_PUSH_RECEIVE** → configured push dataset → **MSG_PUSH_EDGE**. The live-update discriminator forks to the registered `ILiveupdatePlugin`, not the standard push path.
* `handleNotificationResponse` (both overloads) → intent/id/XDM checks → **MSG_PUSH_INTERACTION** → tracking-status response and conditional **MSG_PUSH_EDGE**. With `adb_iam_id`: independently update app-surface propositions → **MSG_PUSH_TO_INAPP** (even if update completion reports false).
* `ContentCard.track` / deprecated `FeedItem.track` → parent schema → `PropositionItem.track` → **MSG_HISTORY**, then **MSG_TRACK_ITEM** → Messaging listener/enrichment → **MSG_TRACK_EDGE**. In contrast, `Message.track` sends **MSG_TRACK_EDGE** directly. `generateInteractionXdm` returns a map and sends nothing.
* `Message.show` / `dismiss` → platform/AEP UI → lifecycle listener → history and optional automatic Edge interaction. `recordDisplay` only produces **MSG_HISTORY**. Neither an attempted presentation nor a UI callback guarantees a displayed experience.
* `Assurance.startSession(url)` → **ASSURANCE_URL** → extension/session validation → PIN authorization UI/session orchestration. No-argument start → **ASSURANCE_QUICK** → debug-build/active-session guards → quick-connect UI. An event dispatch is not successful WebSocket authorization.
* Manual `LiveUpdates.trackLiveUpdateEvent` → parse → canonical type/XDM checks → **LIVE_RECEIVE** → lifecycle listener. `triggerLocalLiveUpdate` → canonical plugin check → **LIVE_RENDER** → renderer gates / **LIVE_DIAGNOSTIC** / conditional tracking. The library dispatches events but is not registered as a MobileCore `Extension`. Its executable initializer does register a Configuration response listener to cache the optional dataset override; the contrary broad “does not subscribe” source comment is not treated as authoritative.
* `Concierge.sendDataHandoff` → **CON_HANDOFF** request → exact name/type/source selector in `ConciergeExtension` → validation → active chat forwarder → HTTP/SSE conversational delivery/rendering → correlated accepted/rejected response → callback.
* Mounted Concierge chat and user interactions → Concierge notification profiles **CON_* ** → `ConciergeExtension` → `ConciergeEventTracker` selector → optional Edge request. The tracker defaults **disabled**. `setEdgeTrackingEnabled` only changes that selector; it does not publish an event. Conversation-ended notification is deliberately **not** forwarded to Edge. Query text/custom XDM and feedback notes are not copied into the reviewed Edge tracking payload.

### Behavioral distinctions and error paths

* Invalid Messaging surface/callback inputs can return silently without a callback. Push validation produces local status callbacks; the 5-second event response timeout becomes `UNKNOWN_ERROR`. `TRACKING_INITIATED` means initiation, not delivery.
* `getPropositions` has a real source mismatch: error response writes `AdobeError.getErrorName()` but facade decodes an integer; an empty propositions list also becomes unexpected error. The documentation preserves this instead of inventing a numeric error schema.
* Repeated in-flight IAM refresh calls coalesce, so API-call counts and EventHub event counts differ. Updates and stream-complete events are not business-success receipts.
* Item/Message interaction XDM uses `_experience.decisioning`, marker values `1`, proposition details, optional item tokens, and optional action details. Interact uses `propositionAction.id/label`; suppression uses `reason`. Tracking depends on retained proposition/schema references. Some released-reference/null-event-type paths are not uniformly null-safe; no blanket exception-free guarantee is made.
* The six proposition enum values have named decisioning markers. The two deprecated push enum values map to pushTracking XDM event types but an empty proposition marker; those compatibility mappings are shown rather than silently treating them as one of the six proposition types. On qualified content-card DISPLAY forwarding only, the extension can add `items[].data.characteristics.servedFromPersistentCache`; original token characteristics remain directly on `items[]`. Evidence: `MessagingEdgeEventType.java:59-99`, `messaging/PropositionInteraction.java:114-130`, `messaging/EdgePersonalizationResponseHandler.java:1487-1601` in Messaging.
* UI observers suppress repeat display/dismiss tracking using the current UI state. An interaction callback returning true prevents default URL opening, not interaction tracking; absent a retained content-card mapping, no item tracking is produced. Evidence: Messaging `messaging/MessagingEventHandler.kt:45-167`.
* `Message` interface defaults and SDK concrete behavior differ: default `recordDisplay` only logs; SDK `InternalMessage` writes history. Default metadata is empty and default autotrack is true. Setters/getters are local operations.
* Live Updates interaction `true` means an identified intent, even if XDM checks prevent tracking. Custom action wins over application-open. Dismiss uses fixed action `Dismiss`. Lifecycle events carry `liveActivity.event`; interaction events omit it.
* Locally raised `localstart` does not immediately dispatch receive tracking. A later backend event with XDM can consume one local-start record and emit catch-up tracking with its original XDM timestamp. Platform promotion, permission, interceptor, stale/out-of-order and style checks remain distinct from return values.
* Live Updates `style_null`, missing notification permission and incompatibility diagnostics do not all imply an aborted render: the reviewed plugin can still notify/track/listen after these diagnostics. In particular a null style can render without a style.
* Concierge authentication provider registration is local. It is consulted for subsequent conversation/feedback requests; timeout fallback is not an EventHub authentication-success event. Context updates are in-memory JSON merge patches and reject reserved `identityMap`/unsupported JSON values.
* Concierge auth-provider timeout defaults to 3000 ms and is clamped to 0–600000 ms. A null/blank result, exception or timeout degrades the turn to no token, rather than producing an authentication event. Public ViewModel permission/welcome/active-state getters and local state methods are inventoried separately from the chat composition lifecycle that publishes opened/closed notifications. Evidence: Concierge `ConciergeAuthTokenHolder.kt:28-108`, `ui/chat/ConciergeChatViewModel.kt:208-246,2007-2042`.
* Handoff callback outcomes are not just “response arrived”: accepted response is produced only from the delivered result; validation, busy/no-session, empty response, delivery and timeout failures have typed reasons. A missing event response maps to `NO_RESPONSE`.
* NotificationBuilder is shared rendering infrastructure. Its standalone constructors can throw on missing context/empty data/invalid payload; the plugin catches `Throwable` and returns null for host fallback. `RemindLaterHandler` schedules a PendingIntent and cancels the visible notification; invalid fire time throws. None is automatically a fixed EventHub producer.

### Scope and completeness policy

The customer inventory counts eligible source-declared facade/model/builder/callback/behavior methods, constructors and Kotlin property APIs (a mutable property includes its getter/setter contract), plus explicit `@JvmOverloads` Java-callable overloads. Eligibility requires both an accessible named owner and a customer integration role; default-public syntax alone is insufficient. Compiler synthetic default-mask methods, data-class `componentN/copy`, enum `values/valueOf/entries`, inherited Android/Compose/Object APIs and constant fields are excluded. Explicit customer-model Java overrides such as `equals`, `hashCode`, `toString` remain inventoried. Kotlin optional parameters without `@JvmOverloads` are documented in the single Kotlin signature, not fictional Java overloads.

Public Java implementation types are not automatically customer APIs. Extension subclasses, internal mappers/caches/engines, platform receivers/activities, telemetry bridges and tests/samples are excluded. Package-private `ContentCard.Builder` and schema constructors are excluded; the public deprecated `FeedItem.Builder` and its methods remain included.

The package-private `MessageRequiredFieldMissingException` is also excluded even though its constructor is declared public. Anonymous callback implementations inside facade methods and internal low-level Compose components/previews are implementation details, not extra facade overloads. Sealed base-class constructors are not public constructors. The source census is not a compiled binary-compatibility audit; no checked-in API baseline was available in the assigned source checkouts.

Shared Compose APIs shipped inside Messaging, the separate `aepsdk-ui-android` NotificationBuilder package, FCM service integration, and the Core-invoked Live Update plugin callback have their own **supporting infrastructure inventory**. They are documented but do not inflate the customer extension API count. This also preserves `MessagingService.handleRemoteMessage` as an explicitly documented host FCM integration entry while separating it from extension facades and its platform lifecycle overrides. Infrastructure callbacks can initiate host-selected work; a callback declaration by itself is **CALLBACK_ONLY**, not a fabricated EventHub event.

Concierge customer theme/configuration models remain individually inventoried because public `ConciergeTheme`, `ConciergeThemeLoader.load/default/loadTokens`, and public theme getters accept or expose that model graph. Their styling/model subtotal is reported separately from other customer APIs. In contrast, `ActiveConciergeTheme` is a private-CompositionLocal implementation holder; `WelcomeConfig`/`SuggestedPrompt` feed the ViewModel's internal welcome state; and six instance methods on `ConciergeThemeLoader` have no public constructor or public instance accessor. These source-public implementation declarations are explicitly excluded, not promoted to customer APIs.

The eligibility audit uses reviewed named owners and declaration roles, not a global public-method regex. Java facade methods must be direct public static declarations; anonymous `run/fail/call` bodies are excluded. Only the outer public UI observer overrides at the verified declaration sites are eligible; anonymous observer delegates are excluded. Kotlin internal/private/protected owners and implementation-only instance surfaces do not become customer APIs because a contained declaration has no explicit visibility modifier. Producers from these implementation paths remain profile evidence.

### Link metadata and catalog eligibility

Examples show only normalized `eventName/eventType/eventSource/eventData`; placeholders are safe, not real identifiers, configuration, tokens, URLs or user data. Optional fields shown together are illustrative and must be omitted on branches described by each profile. Fixed SDK flags remain literal.

Every `Event.Builder` creates a fresh event identifier/build timestamp. Unless the producer explicitly links it, `parentId=null` and `responseId=null`. `inResponseToEvent` sets response correlation to the request identifier; `chainToParentEvent` supplies the parent link. Fetch chains to its Messaging request; finalize chains to the Edge complete event and also writes `endingEventId`. Push Edge chains to its Messaging input. Message/item Edge interaction sends a null parent, despite the call relationship. Live Updates has no explicit parent/response links; catch-up XDM timestamp does not override the Event timestamp.

Fixed producer profiles are catalog eligible. Local history/diagnostic profiles require a local/non-network label. Model maps, callbacks, UI objects, scheduled intents, dynamic providers and HTTP/SSE transport are not fixed extension events. Event type `edge` identifies routing, not API ownership: the publishing code may belong to Messaging, Live Updates or Concierge.

<!-- Generated source-declaration inventory and profile appendix follow. -->

### Eligible customer API inventories

**951 eligible entries**: 245 facade/model/builder/callback/behavior entries plus 706 customer theme/configuration entries. Supporting infrastructure (267) and implementation-only declarations (22) are separate and excluded from this count.

#### Messaging

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0001 | `Message` | `void track(final String interaction, final MessagingEdgeEventType eventType)` | MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:27` |
| MUI-0002 | `Message` | `void recordDisplay()` | MSG_HISTORY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:35` |
| MUI-0003 | `Message` | `void show()` | MSG_PRESENT, MSG_TRACK_EDGE, MSG_HISTORY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:40` |
| MUI-0004 | `Message` | `void dismiss()` | MSG_PRESENT, MSG_TRACK_EDGE, MSG_HISTORY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:43` |
| MUI-0005 | `Message` | `String getId()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:46` |
| MUI-0006 | `Message` | `void setAutoTrack(final boolean enabled)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:54` |
| MUI-0007 | `Message` | `boolean getAutoTrack()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:61` |
| MUI-0008 | `Message` | `Map<String, Object> getMetadata()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:72` |
| MUI-0009 | `MessagingPushPayload` | `public MessagingPushPayload(final RemoteMessage message)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:131` |
| MUI-0010 | `MessagingPushPayload` | `public MessagingPushPayload(final Map<String, String> data)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:167` |
| MUI-0011 | `MessagingPushPayload` | `public MessagingPushPayload(@NonNull final Intent intent)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:180` |
| MUI-0012 | `MessagingPushPayload` | `public String getTitle()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:211` |
| MUI-0013 | `MessagingPushPayload` | `public String getBody()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:215` |
| MUI-0014 | `MessagingPushPayload` | `public String getSound()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:219` |
| MUI-0015 | `MessagingPushPayload` | `public int getBadgeCount()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:223` |
| MUI-0016 | `MessagingPushPayload` | `public int getNotificationPriority()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:227` |
| MUI-0017 | `MessagingPushPayload` | `public int getNotificationVisibility()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:231` |
| MUI-0018 | `MessagingPushPayload` | `public int getNotificationImportance()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:235` |
| MUI-0019 | `MessagingPushPayload` | `public String getChannelId()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:239` |
| MUI-0020 | `MessagingPushPayload` | `public String getIcon()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:243` |
| MUI-0021 | `MessagingPushPayload` | `public String getImageUrl()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:247` |
| MUI-0022 | `MessagingPushPayload` | `public String getMessageId()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:251` |
| MUI-0023 | `MessagingPushPayload` | `public ActionType getActionType()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:258` |
| MUI-0024 | `MessagingPushPayload` | `public String getActionUri()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:262` |
| MUI-0025 | `MessagingPushPayload` | `public List<ActionButton> getActionButtons()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:271` |
| MUI-0026 | `MessagingPushPayload` | `public Map<String, String> getData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:275` |
| MUI-0027 | `MessagingPushPayload` | `public void putDataInExtras(final Intent intent)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:336` |
| MUI-0028 | `MessagingPushPayload.ActionButton` | `public ActionButton(final String label, final String link, final String type)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:470` |
| MUI-0029 | `MessagingPushPayload.ActionButton` | `public String getLabel()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:476` |
| MUI-0030 | `MessagingPushPayload.ActionButton` | `public String getLink()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:480` |
| MUI-0031 | `MessagingPushPayload.ActionButton` | `public ActionType getType()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:484` |
| MUI-0032 | `PushNotificationListener` | `void onNotificationReceived(@NonNull MessagingPushPayload payload)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:34` |
| MUI-0033 | `PushNotificationListener` | `void onNotificationOpened( @NonNull MessagingPushPayload payload, @Nullable String actionButtonId)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:46` |
| MUI-0034 | `PushNotificationListener` | `void onNotificationDismissed(@NonNull MessagingPushPayload payload)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:57` |
| MUI-0035 | `MessagingEdgeEventType` | `public int getValue()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:55` |
| MUI-0036 | `MessagingEdgeEventType` | `public String getPropositionEventType()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:59` |
| MUI-0037 | `MessagingEdgeEventType` | `public String toString()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:79` |
| MUI-0038 | `Messaging` | `public static String extensionVersion()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:93` |
| MUI-0039 | `Messaging` | `public static boolean addPushTrackingDetails( @NonNull final Intent intent, @NonNull final String messageId, final @NonNull Map<String, String> data)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:110` |
| MUI-0040 | `Messaging` | `public static void trackPushReceived(@NonNull final RemoteMessage remoteMessage)` | MSG_PUSH_RECEIVE, MSG_PUSH_EDGE | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:171` |
| MUI-0041 | `Messaging` | `public static void handleNotificationResponse( @NonNull final Intent intent, final boolean applicationOpened, @Nullable final String customActionId)` | MSG_PUSH_INTERACTION, MSG_PUSH_EDGE, MSG_PUSH_TO_INAPP, MSG_UPDATE, MSG_FETCH | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:224` |
| MUI-0042 | `Messaging` | `public static void handleNotificationResponse( @NonNull final Intent intent, final boolean applicationOpened, @Nullable final String customActionId, @Nullable final AdobeCallback<PushTrackingStatus> callback)` | MSG_PUSH_INTERACTION, MSG_PUSH_EDGE, MSG_PUSH_TO_INAPP, MSG_UPDATE, MSG_FETCH | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:242` |
| MUI-0046 | `Messaging` | `public static void refreshInAppMessages()` | MSG_REFRESH, MSG_FETCH, MSG_NOTIFICATION | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:396` |
| MUI-0047 | `Messaging` | `public static void getPropositionsForSurfaces( @NonNull final List<Surface> surfaces, @NonNull final AdobeCallback<Map<Surface, List<Proposition>>> callback)` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:413` |
| MUI-0050 | `Messaging` | `public static void clearCachedPropositions()` | MSG_CLEAR | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:521` |
| MUI-0051 | `Messaging` | `public static void updatePropositionsForSurfaces(@NonNull final List<Surface> surfaces)` | MSG_UPDATE, MSG_FETCH, MSG_NOTIFICATION | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:543` |
| MUI-0052 | `Messaging` | `public static void updatePropositionsForSurfaces( @NonNull final List<Surface> surfaces, @Nullable final AdobeCallback<Boolean> callback)` | MSG_UPDATE, MSG_FETCH, MSG_NOTIFICATION | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:556` |
| MUI-0053 | `Messaging` | `public static void updatePropositionsForSurfaces( @NonNull final List<Surface> surfaces, @Nullable final Map<String, Object> xdm, @Nullable final Map<String, Object> data)` | MSG_UPDATE, MSG_FETCH, MSG_NOTIFICATION | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:580` |
| MUI-0054 | `Messaging` | `public static void updatePropositionsForSurfaces( @NonNull final List<Surface> surfaces, @Nullable final Map<String, Object> xdm, @Nullable final Map<String, Object> data, @Nullable final AdobeCallback<Boolean> callback)` | MSG_UPDATE, MSG_FETCH, MSG_NOTIFICATION | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:607` |
| MUI-0055 | `Messaging` | `public static void setPushNotificationListener( @Nullable final PushNotificationListener listener)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:740` |
| MUI-0056 | `Proposition` | `public Proposition( @NonNull final String uniqueId, @NonNull final String scope, @NonNull final Map<String, Object> scopeDetails, @NonNull final List<PropositionItem> propositionItems) throws MessageRequiredFieldMissingException` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:48` |
| MUI-0057 | `Proposition` | `public String getUniqueId()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:76` |
| MUI-0058 | `Proposition` | `public List<PropositionItem> getItems()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:85` |
| MUI-0059 | `Proposition` | `public String getScope()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:94` |
| MUI-0060 | `Proposition` | `public int getPriority()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:104` |
| MUI-0061 | `Proposition` | `public static Proposition fromEventData(final Map<String, Object> eventData)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:172` |
| MUI-0062 | `Proposition` | `public Map<String, Object> toEventData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:207` |
| MUI-0063 | `Proposition` | `public boolean equals(final Object object)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:226` |
| MUI-0064 | `PropositionItem` | `public PropositionItem( @NonNull final String itemId, @NonNull final SchemaType schema, @NonNull final Map<String, Object> itemData) throws MessageRequiredFieldMissingException` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:57` |
| MUI-0065 | `PropositionItem` | `public String getItemId()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:75` |
| MUI-0066 | `PropositionItem` | `public SchemaType getSchema()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:84` |
| MUI-0067 | `PropositionItem` | `public Map<String, Object> getItemData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:93` |
| MUI-0068 | `PropositionItem` | `public void track(@NonNull final MessagingEdgeEventType eventType)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:112` |
| MUI-0069 | `PropositionItem` | `public void track( final String interaction, @NonNull final MessagingEdgeEventType eventType, final List<String> tokens)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:124` |
| MUI-0070 | `PropositionItem` | `public Map<String, Object> generateInteractionXdm( @NonNull final MessagingEdgeEventType eventType)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:170` |
| MUI-0071 | `PropositionItem` | `public Map<String, Object> generateInteractionXdm( final String interaction, @NonNull final MessagingEdgeEventType eventType, final List<String> tokens)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:186` |
| MUI-0072 | `PropositionItem` | `public Map<String, Object> getJsonContentMap()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:214` |
| MUI-0073 | `PropositionItem` | `public List<Map<String, Object>> getJsonContentArrayList()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:230` |
| MUI-0074 | `PropositionItem` | `public String getHtmlContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:244` |
| MUI-0075 | `PropositionItem` | `public InAppSchemaData getInAppSchemaData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:258` |
| MUI-0076 | `PropositionItem` | `public ContentCardSchemaData getContentCardSchemaData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:271` |
| MUI-0077 | `PropositionItem` | `public FeedItemSchemaData getFeedItemSchemaData()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:292` |
| MUI-0078 | `PropositionItem` | `public InboxContentSchemaData getInboxSchemaData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:313` |
| MUI-0079 | `Surface` | `public Surface(final String path)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:38` |
| MUI-0080 | `Surface` | `public Surface()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:42` |
| MUI-0081 | `Surface` | `public String getUri()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:73` |
| MUI-0082 | `Surface` | `public boolean isValid()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:77` |
| MUI-0083 | `Surface` | `public int hashCode()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:88` |
| MUI-0084 | `Surface` | `public boolean equals(final Object object)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:92` |
| MUI-0085 | `Surface` | `public String toString()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:102` |
| MUI-0086 | `Surface` | `public Map<String, Object> toEventData()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:111` |
| MUI-0087 | `Surface` | `public static Surface fromEventData(final Map<String, Object> data)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:117` |
| MUI-0088 | `ContentCard` | `public String getTitle()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:168` |
| MUI-0089 | `ContentCard` | `public String getBody()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:177` |
| MUI-0090 | `ContentCard` | `public String getImageUrl()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:186` |
| MUI-0091 | `ContentCard` | `public String getActionUrl()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:195` |
| MUI-0092 | `ContentCard` | `public String getActionTitle()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:204` |
| MUI-0093 | `ContentCard` | `public void track(final String interaction, final MessagingEdgeEventType eventType)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:215` |
| MUI-0094 | `FeedItem.Builder` | `public Builder(final String title, final String body)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:62` |
| MUI-0095 | `FeedItem.Builder` | `public Builder setImageUrl(final String imageUrl)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:80` |
| MUI-0096 | `FeedItem.Builder` | `public Builder setActionUrl(final String actionUrl)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:95` |
| MUI-0097 | `FeedItem.Builder` | `public Builder setActionTitle(final String actionTitle)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:110` |
| MUI-0098 | `FeedItem.Builder` | `public Builder setParent(final FeedItemSchemaData parent)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:126` |
| MUI-0099 | `FeedItem.Builder` | `public FeedItem build()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:138` |
| MUI-0100 | `FeedItem` | `public String getTitle()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:164` |
| MUI-0101 | `FeedItem` | `public String getBody()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:173` |
| MUI-0102 | `FeedItem` | `public String getImageUrl()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:182` |
| MUI-0103 | `FeedItem` | `public String getActionUrl()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:191` |
| MUI-0104 | `FeedItem` | `public String getActionTitle()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:200` |
| MUI-0105 | `FeedItem` | `public void track(final String interaction, final MessagingEdgeEventType eventType)` (deprecated) | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:211` |
| MUI-0106 | `Feed` | `public Feed(final String name, final Surface surface, final List<FeedItem> items)` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:39` |
| MUI-0107 | `Feed` | `public String getName()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:50` |
| MUI-0108 | `Feed` | `public String getSurfaceUri()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:59` |
| MUI-0109 | `Feed` | `public List<FeedItem> getItems()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:68` |
| MUI-0110 | `ContentCardSchemaData` | `public Object getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:67` |
| MUI-0111 | `ContentCardSchemaData` | `public ContentType getContentType()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:71` |
| MUI-0112 | `ContentCardSchemaData` | `public int getPublishedDate()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:75` |
| MUI-0113 | `ContentCardSchemaData` | `public int getExpiryDate()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:79` |
| MUI-0114 | `ContentCardSchemaData` | `public Map<String, Object> getMeta()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:83` |
| MUI-0115 | `ContentCardSchemaData` | `public ContentCard getContentCard()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:88` |
| MUI-0116 | `ContentCardSchemaData` | `public void track(final String interaction, final MessagingEdgeEventType eventType)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:126` |
| MUI-0117 | `FeedItemSchemaData` | `public Object getContent()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:74` |
| MUI-0118 | `FeedItemSchemaData` | `public ContentType getContentType()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:78` |
| MUI-0119 | `FeedItemSchemaData` | `public int getPublishedDate()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:82` |
| MUI-0120 | `FeedItemSchemaData` | `public int getExpiryDate()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:86` |
| MUI-0121 | `FeedItemSchemaData` | `public Map<String, Object> getMeta()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:90` |
| MUI-0122 | `FeedItemSchemaData` | `public FeedItem getFeedItem()` (deprecated) | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:99` |
| MUI-0123 | `FeedItemSchemaData` | `public void track(final String interaction, final MessagingEdgeEventType eventType)` (deprecated) | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:137` |
| MUI-0124 | `InAppSchemaData` | `public Object getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:99` |
| MUI-0125 | `InAppSchemaData` | `public ContentType getContentType()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:103` |
| MUI-0126 | `InAppSchemaData` | `public int getPublishedDate()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:107` |
| MUI-0127 | `InAppSchemaData` | `public int getExpiryDate()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:111` |
| MUI-0128 | `InAppSchemaData` | `public Map<String, Object> getMeta()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:115` |
| MUI-0129 | `InAppSchemaData` | `public Map<String, Object> getMobileParameters()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:119` |
| MUI-0130 | `InAppSchemaData` | `public Map<String, Object> getWebParameters()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:123` |
| MUI-0131 | `InAppSchemaData` | `public List<String> getRemoteAssets()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:127` |
| MUI-0132 | `JsonContentSchemaData` | `public Object getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/JsonContentSchemaData.java:70` |
| MUI-0133 | `HtmlContentSchemaData` | `public String getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/HtmlContentSchemaData.java:47` |
| MUI-0134 | `HtmlContentSchemaData` | `public ContentType getFormat()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/HtmlContentSchemaData.java:51` |
| MUI-0135 | `InboxContentSchemaData` | `public Map<String, Object> getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxContentSchemaData.java:44` |
| MUI-0136 | `InboxContentSchemaData` | `public Map<String, Object> getMetadata()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxContentSchemaData.java:48` |
| MUI-0137 | `SchemaData` | `Object getContent()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaData.java:16` |
| MUI-0138 | `PushTrackingStatus` | `public String getDescription()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:49` |
| MUI-0139 | `PushTrackingStatus` | `public int getValue()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:56` |
| MUI-0140 | `PushTrackingStatus` | `public static PushTrackingStatus fromInt(final int value)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:66` |
| MUI-0141 | `ContentType` | `public int getValue()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentType.java:27` |
| MUI-0142 | `ContentType` | `public String toString()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentType.java:32` |
| MUI-0143 | `SchemaType` | `public int getValue()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaType.java:39` |
| MUI-0144 | `SchemaType` | `public String toString()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaType.java:44` |

#### Messaging UI integration

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0148 | `ContentCardUIProvider` | `fun getContentCardUIFlow(): Flow<Result<List<AepUI<*, *>>>>` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:88` |
| MUI-0149 | `ContentCardUIProvider` | `suspend fun getContentCardUI(): Flow<Result<List<AepUI<*, *>>>>` (deprecated) | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:106` |
| MUI-0150 | `ContentCardUIProvider` | `suspend fun refreshContent()` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:119` |
| MUI-0151 | `ContentCardUIProvider` | `suspend fun getContent(): Flow<Result<List<AepUITemplate>>>` (deprecated) | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:148` |
| MUI-0152 | `ContentCardUIProvider` | `fun getUIContent(): Flow<Result<List<AepUITemplate>>>` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:164` |
| MUI-0153 | `ContentCardUIProvider` | `ContentCardUIProvider(val surface: Surface)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:42` |
| MUI-0154 | `MessagingInboxProvider` | `fun getInboxUI(): Flow<InboxUIState>` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:91` |
| MUI-0155 | `MessagingInboxProvider` | `suspend fun refresh()` | MSG_GET | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:107` |
| MUI-0156 | `MessagingInboxProvider` | `MessagingInboxProvider(val surface: Surface)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:38` |
| MUI-0157 | `MessagingInboxProvider.InboxProposition` | `MessagingInboxProvider.InboxProposition(val inbox: Proposition, val contentCards: List<Proposition>)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:41` |
| MUI-0158 | `MessagingInboxProvider` | `val surface: Surface` [g/e/t] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:39` |
| MUI-0159 | `MessagingInboxProvider.InboxProposition` | `val inbox: Proposition` [g/e/t] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:42` |
| MUI-0160 | `MessagingInboxProvider.InboxProposition` | `val contentCards: List<Proposition>` [g/e/t] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:43` |
| MUI-0162 | `ContentCardEventObserver` | `fun onEvent(event: UIEvent<*, *>)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:60` |
| MUI-0163 | `ContentCardEventObserver` | `ContentCardEventObserver(private val callback: ContentCardUIEventListener? = null, private val provider: ContentCardUIProvider? = null)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:34` |
| MUI-0164 | `ContentCardEventObserver` | `ContentCardEventObserver(private val callback: ContentCardUIEventListener? = null)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:34` |
| MUI-0165 | `ContentCardEventObserver` | `ContentCardEventObserver()` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:34` |
| MUI-0168 | `InboxEventObserver` | `fun onInboxEvent(event: InboxEvent)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxEventObserver.kt:61` |
| MUI-0169 | `InboxEventObserver` | `fun onEvent(event: UIEvent<*, *>)` | MSG_TRACK_ITEM, MSG_HISTORY, MSG_TRACK_EDGE | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxEventObserver.kt:65` |
| MUI-0170 | `InboxEventObserver` | `InboxEventObserver(private val provider: MessagingInboxProvider, private val itemEventObserver: AepUIEventObserver? = null)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxEventObserver.kt:29` |
| MUI-0171 | `ContentCardAepUIExtensionsKt` | `fun AepUI<*, *>.getMeta(): Map<String, Any>?` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardAepUIExtensions.kt:22` |
| MUI-0172 | `UiTemplateConstructionFailedException` | `UiTemplateConstructionFailedException(message: String)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/UiTemplateConstructionFailedException.kt:19` |
| MUI-1211 | `ContentCardUIEventListener` | `fun onDisplay(aepUI: AepUI<*, *>)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:75` |
| MUI-1212 | `ContentCardUIEventListener` | `fun onDismiss(aepUI: AepUI<*, *>)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:82` |
| MUI-1213 | `ContentCardUIEventListener` | `fun onInteract(aepUI: AepUI<*, *>, interactionId: String?, actionUrl: String?): Boolean` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:92` |
| MUI-1243 | `ContentCardUIProvider` | `val surface: Surface` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:42` |

#### Assurance

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0412 | `Assurance` | `public static String extensionVersion()` | NO_EVENT | `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:42` |
| MUI-0413 | `Assurance` | `public static void startSession(@NonNull final String url)` | ASSURANCE_URL | `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:54` |
| MUI-0414 | `Assurance` | `public static void startSession()` | ASSURANCE_QUICK | `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:87` |

#### Live Updates (class library/plugin)

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0415 | `LiveUpdates` | `fun extensionVersion(): String` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:208` |
| MUI-0416 | `LiveUpdates` | `fun setLiveUpdateListener(listener: ILiveUpdateListener?)` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:218` |
| MUI-0417 | `LiveUpdates` | `fun getLiveUpdateListener(): ILiveUpdateListener?` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:224` |
| MUI-0418 | `LiveUpdates` | `fun setLiveUpdateInterceptor(interceptor: ILiveUpdateInterceptor?)` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:235` |
| MUI-0419 | `LiveUpdates` | `fun getLiveUpdateInterceptor(): ILiveUpdateInterceptor?` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:241` |
| MUI-0420 | `LiveUpdates` | `fun trackLiveUpdateEvent(context: Context, message: RemoteMessage)` | LIVE_RECEIVE | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:275` |
| MUI-0421 | `LiveUpdates` | `fun triggerLocalLiveUpdate(context: Context, payload: LiveUpdatePayload): Boolean` | LIVE_RENDER, LIVE_RECEIVE, LIVE_DIAGNOSTIC | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:310` |
| MUI-0422 | `LiveUpdates` | `fun addPushTrackingDetails(intent: Intent?, message: RemoteMessage?): Boolean` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:342` |
| MUI-0423 | `LiveUpdates` | `fun handleNotificationResponse(intent: Intent?, applicationOpened: Boolean, customActionId: String? = null): Boolean` | LIVE_INTERACTION | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:374` |
| MUI-0424 | `LiveUpdates` | `fun handleNotificationResponse(intent: Intent?, applicationOpened: Boolean): Boolean` | LIVE_INTERACTION | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:374` |
| MUI-0425 | `LiveUpdates` | `fun trackTopicSubscribed(payload: LiveUpdatePayload)` | LIVE_TOPIC | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:423` |
| MUI-0426 | `LiveUpdates` | `fun trackTopicUnsubscribed(payload: LiveUpdatePayload)` | LIVE_TOPIC | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:442` |
| MUI-0427 | `LiveUpdatePayload` | `fun toString(): String` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:84` |
| MUI-0428 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null, dismissAfterSeconds: Long? = null, contentState: JSONObject? = null, topicName: String? = null, smallIcon: String? = null, xdm: JSONObject? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0429 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null, dismissAfterSeconds: Long? = null, contentState: JSONObject? = null, topicName: String? = null, smallIcon: String? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0430 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null, dismissAfterSeconds: Long? = null, contentState: JSONObject? = null, topicName: String? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0431 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null, dismissAfterSeconds: Long? = null, contentState: JSONObject? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0432 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null, dismissAfterSeconds: Long? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0433 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null, whenSeconds: Long? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0434 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null, criticalText: String? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0435 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null, body: String? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0436 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long, priority: String? = null): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0437 | `LiveUpdatePayload` | `fun create(notificationId: String, channelId: String, eventType: String, title: String?, timestamp: Long): LiveUpdatePayload` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168` |
| MUI-0438 | `LiveUpdatePayload` | `fun isLiveUpdate(message: RemoteMessage): Boolean` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:222` |
| MUI-0439 | `LiveUpdatePayload` | `fun parse(message: RemoteMessage): LiveUpdatePayload?` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:231` |
| MUI-0440 | `LiveUpdatePayload` | `val notificationId: String` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:41` |
| MUI-0441 | `LiveUpdatePayload` | `val channelId: String` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:42` |
| MUI-0442 | `LiveUpdatePayload` | `val eventType: String` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:43` |
| MUI-0443 | `LiveUpdatePayload` | `val title: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:44` |
| MUI-0444 | `LiveUpdatePayload` | `val timestamp: Long` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:45` |
| MUI-0445 | `LiveUpdatePayload` | `val priority: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:46` |
| MUI-0446 | `LiveUpdatePayload` | `val body: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:47` |
| MUI-0447 | `LiveUpdatePayload` | `val criticalText: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:48` |
| MUI-0448 | `LiveUpdatePayload` | `val whenSeconds: Long?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:49` |
| MUI-0449 | `LiveUpdatePayload` | `val dismissAfterSeconds: Long?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:50` |
| MUI-0450 | `LiveUpdatePayload` | `val contentState: JSONObject?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:51` |
| MUI-0451 | `LiveUpdatePayload` | `val topicName: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:55` |
| MUI-0452 | `LiveUpdatePayload` | `val smallIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:60` |
| MUI-0453 | `LiveUpdatePayload` | `val xdm: JSONObject?` [g/e/t] | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:66` |
| MUI-0455 | `LiveUpdatePlugin` | `LiveUpdatePlugin(private val styleProvider: ILiveUpdateStyleProvider)` | NO_EVENT | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePlugin.kt:56` |
| MUI-0456 | `ILiveUpdateListener` | `fun onLiveUpdateReceived(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:39` |
| MUI-0457 | `ILiveUpdateListener` | `fun onStart(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:42` |
| MUI-0458 | `ILiveUpdateListener` | `fun onUpdate(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:45` |
| MUI-0459 | `ILiveUpdateListener` | `fun onEnd(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:48` |
| MUI-0460 | `ILiveUpdateListener` | `fun onDismissed(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:65` |
| MUI-0461 | `ILiveUpdateListener` | `fun onClick(payload: LiveUpdatePayload)` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:79` |
| MUI-0462 | `ILiveUpdateInterceptor` | `fun shouldDisplayLiveUpdate(payload: LiveUpdatePayload): Boolean` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateInterceptor.kt:44` |
| MUI-0463 | `ILiveUpdateStyleProvider` | `fun provideStyle(payload: LiveUpdatePayload): NotificationCompat.Style?` | CALLBACK_ONLY | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateStyleProvider.kt:32` |

#### Concierge

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0464 | `Concierge` | `fun extensionVersion(): String` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:27` |
| MUI-0465 | `Concierge` | `fun setEdgeTrackingEnabled(enabled: Boolean)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:34` |
| MUI-0466 | `Concierge` | `fun setAuthTokenProvider(provider: ConciergeAuthTokenProvider?, timeoutMillis: Long = ConciergeAuthTokenHolder.DEFAULT_PROVIDE_TOKEN_TIMEOUT_MS)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:51` |
| MUI-0467 | `Concierge` | `fun setAuthTokenProvider(provider: ConciergeAuthTokenProvider?)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:51` |
| MUI-0468 | `Concierge` | `fun updateXDMContext(fields: Map<String, Any?>)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:80` |
| MUI-0469 | `Concierge` | `fun sendDataHandoff(routingHint: String = "", xdmFields: Map<String, Any>, localMessage: String? = null, completion: ConciergeDataHandoffCallback? = null)` | CON_HANDOFF | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:106` |
| MUI-0470 | `Concierge` | `fun sendDataHandoff(routingHint: String = "", xdmFields: Map<String, Any>, localMessage: String? = null)` | CON_HANDOFF | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:106` |
| MUI-0471 | `Concierge` | `fun sendDataHandoff(routingHint: String = "", xdmFields: Map<String, Any>)` | CON_HANDOFF | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:106` |
| MUI-0472 | `Concierge` | `fun sendDataHandoff(xdmFields: Map<String, Any>)` | CON_HANDOFF | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:106` |
| MUI-0474 | `ConciergeAuthTokenProvider` | `fun provideToken(): String?` | CALLBACK_ONLY | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeAuthTokenProvider.kt:32` |
| MUI-0475 | `ConciergeDataHandoffCallback` | `fun onResult(accepted: Boolean, rejectReason: ConciergeDataHandoffRejectReason?)` | CALLBACK_ONLY | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeDataHandoffCallback.kt:29` |
| MUI-1223 | `ConciergeDataHandoffRejectReason` | `val rawValue: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeDataHandoffRejectReason.kt:21` |

#### Concierge chat UI

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0476 | `ConciergeChatKt` | `fun ConciergeChat(modifier: Modifier = Modifier, viewModel: ConciergeChatViewModel, surfaces: List<String>? = null, handleLink: LinkHandler? = null, content: @Composable (showChat: () -> Unit) -> Unit)` | CON_SESSION_INITIALIZED, CON_CHAT_OPENED, CON_CHAT_CLOSED, CON_CONVERSATION_ENDED, CON_QUERY_SUBMITTED, CON_PROMPT_SUGGESTION_CLICKED, CON_WELCOME_PROMPT_SUGGESTION_CLICKED, CON_CARD_CLICKED, CON_MIC_BUTTON_CLICKED, CON_RESPONSE_STARTED, CON_RESPONSE_COMPLETED, CON_CARDS_RENDERED, CON_FEEDBACK_SUBMITTED, CON_ERROR_OCCURRED, CON_DISCLAIMER_LINK_CLICKED, CON_CTA_BUTTON_CLICKED, CON_LINK_CLICKED | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChat.kt:116` |
| MUI-0477 | `ConciergeChatKt` | `fun ConciergeChat(viewModel: ConciergeChatViewModel, onClose: () -> Unit, modifier: Modifier = Modifier, handleLink: LinkHandler? = null)` | CON_SESSION_INITIALIZED, CON_CHAT_OPENED, CON_CHAT_CLOSED, CON_CONVERSATION_ENDED, CON_QUERY_SUBMITTED, CON_PROMPT_SUGGESTION_CLICKED, CON_WELCOME_PROMPT_SUGGESTION_CLICKED, CON_CARD_CLICKED, CON_MIC_BUTTON_CLICKED, CON_RESPONSE_STARTED, CON_RESPONSE_COMPLETED, CON_CARDS_RENDERED, CON_FEEDBACK_SUBMITTED, CON_ERROR_OCCURRED, CON_DISCLAIMER_LINK_CLICKED, CON_CTA_BUTTON_CLICKED, CON_LINK_CLICKED | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChat.kt:221` |
| MUI-0478 | `ConciergeChatKt` | `typealias LinkHandler = (url: String) -> Boolean` | CALLBACK_ONLY | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChat.kt:208` |
| MUI-0479 | `ConciergeChatView` | `fun bind(lifecycleOwner: LifecycleOwner, viewModelStoreOwner: ViewModelStoreOwner, surfaces: List<String>? = null, theme: ConciergeThemeData? = null, handleLink: ((String) -> Boolean)? = null, onClose: () -> Unit)` | CON_CHAT_OPENED, CON_CHAT_CLOSED | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:108` |
| MUI-0480 | `ConciergeChatView` | `fun bind(lifecycleOwner: LifecycleOwner, viewModelStoreOwner: ViewModelStoreOwner, surfaces: List<String>? = null, theme: ConciergeThemeData? = null, handleLink: ((String) -> Boolean)? = null, triggerView: View)` | CON_CHAT_OPENED, CON_CHAT_CLOSED | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:152` |
| MUI-0481 | `ConciergeChatView` | `ConciergeChatView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:74` |
| MUI-0482 | `ConciergeChatView` | `ConciergeChatView(context: Context, attrs: AttributeSet? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:74` |
| MUI-0483 | `ConciergeChatView` | `ConciergeChatView(context: Context)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:74` |
| MUI-1214 | `ConciergeChatViewModel` | `ConciergeChatViewModel(application: Application)` | CON_SESSION_INITIALIZED | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:501` |
| MUI-1215 | `ConciergeChatViewModel` | `fun updateWelcomeConfigFromTheme(themeConfig: ConciergeThemeConfig?)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:230` |
| MUI-1216 | `ConciergeChatViewModel` | `fun dismissWelcomeCard()` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:611` |
| MUI-1217 | `ConciergeChatViewModel` | `fun refreshPermissionStatus()` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:2007` |
| MUI-1218 | `ConciergeChatViewModel` | `fun openConcierge()` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:2016` |
| MUI-1219 | `ConciergeChatViewModel` | `fun closeConcierge()` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:2040` |
| MUI-1220 | `ConciergeChatViewModel` | `val hasAudioPermission: StateFlow<Boolean>` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:208` |
| MUI-1221 | `ConciergeChatViewModel` | `val showWelcomeCard: StateFlow<Boolean>` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:214` |
| MUI-1222 | `ConciergeChatViewModel` | `val isConciergeActive: StateFlow<Boolean>` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:246` |

#### Concierge customer theme/configuration models

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0484 | `ConciergeThemeConfig` | `ConciergeThemeConfig(val name: String? = null, val colors: ConciergeThemeColors? = null, val styles: ConciergeThemeStyles? = null, val header: HeaderConfig? = null, val text: ConciergeTextStrings? = null, val disclaimer: DisclaimerConfig? = null, val welcomeExamples: List<ConciergeWelcomeExample>? = null, val feedbackPositiveOptions: List<String>? = null, val feedbackNegativeOptions: List<String>? = null, val typography: ConciergeTypographyConfig? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:24` |
| MUI-0485 | `HeaderConfig` | `HeaderConfig(val title: String? = null, val subtitle: String? = null, val image: String? = null, val layoutType: String? = null, val imageHeight: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:40` |
| MUI-0486 | `ConciergeTypographyConfig` | `ConciergeTypographyConfig(val inputFontSize: Double? = null, val disclaimerFontSize: Double? = null, val disclaimerFontWeight: Int? = null, val citationsFontSize: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:62` |
| MUI-0487 | `ConciergeWelcomeExample` | `ConciergeWelcomeExample(val text: String, val image: String? = null, val backgroundColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:72` |
| MUI-0488 | `ConciergeTextStrings` | `ConciergeTextStrings(val inputPlaceholder: String? = null, val inputAiChatIconTooltip: String? = null, val welcomeHeading: String? = null, val welcomeSubheading: String? = null, val loadingMessage: String? = null, val feedbackDialogTitlePositive: String? = null, val feedbackDialogTitleNegative: String? = null, val feedbackDialogQuestionPositive: String? = null, val feedbackDialogQuestionNegative: String? = null, val feedbackDialogNotes: String? = null, val feedbackDialogSubmit: String? = null, val feedbackDialogCancel: String? = null, val feedbackDialogNotesPlaceholder: String? = null, val feedbackToastSuccess: String? = null, val feedbackHelpfulLabel: String? = null, val sourcesLabel: String? = null, val suggestionsHeader: String? = null, val errorNetwork: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:103` |
| MUI-0489 | `DisclaimerConfig` | `DisclaimerConfig(val text: String? = null, val links: List<DisclaimerLink>? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:136` |
| MUI-0490 | `PrivacyNoticeConfig` | `PrivacyNoticeConfig(val title: String? = null, val text: String = "", val links: List<DisclaimerLink> = emptyList())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:147` |
| MUI-0491 | `DisclaimerLink` | `DisclaimerLink(val text: String, val url: String)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:156` |
| MUI-0492 | `ConciergeThemeColors` | `ConciergeThemeColors(val primary: String? = null, val onPrimary: String? = null, val secondary: String? = null, val surface: String? = null, val onSurface: String? = null, val onSurfaceVariant: String? = null, val background: String? = null, val container: String? = null, val outline: String? = null, val error: String? = null, val onError: String? = null, val disclaimer: String? = null, val primaryColors: ConciergePrimaryColors? = null, val surfaceColors: ConciergeSurfaceColors? = null, val message: ConciergeMessageColors? = null, val button: ConciergeButtonColors? = null, val input: ConciergeInputColors? = null, val feedback: ConciergeFeedbackColors? = null, val citation: ConciergeCitationColors? = null, val welcomePrompt: ConciergeWelcomePromptColors? = null, val ctaButton: ConciergeCtaButtonColors? = null, val productCardCtaButton: ConciergeProductCardCtaButtonColors? = null, val productCardSecondaryCtaButton: ConciergeProductCardSecondaryCtaButtonColors? = null, val promptSuggestion: ConciergeWelcomePromptColors? = null, val thinking: ConciergeThinkingColors? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:165` |
| MUI-0493 | `ConciergePrimaryColors` | `ConciergePrimaryColors(val primary: String? = null, val text: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:199` |
| MUI-0494 | `ConciergeSurfaceColors` | `ConciergeSurfaceColors(val mainContainerBackground: String? = null, val mainContainerBottomBackground: String? = null, val messageBlockerBackground: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:204` |
| MUI-0495 | `ConciergeMessageColors` | `ConciergeMessageColors(val userBackground: String? = null, val userText: String? = null, val conciergeBackground: String? = null, val conciergeText: String? = null, val conciergeLink: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:210` |
| MUI-0496 | `ConciergeButtonColors` | `ConciergeButtonColors(val primaryBackground: String? = null, val primaryText: String? = null, val primaryHover: String? = null, val secondaryBorder: String? = null, val secondaryText: String? = null, val secondaryHover: String? = null, val secondaryHoverText: String? = null, val submitFill: String? = null, val submitFillDisabled: String? = null, val submitText: String? = null, val submitTextHover: String? = null, val disabledBackground: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:218` |
| MUI-0497 | `ConciergeInputColors` | `ConciergeInputColors(val background: String? = null, val text: String? = null, val outline: String? = null, val outlineGradient: ConciergeGradientColors? = null, val outlineFocus: String? = null, val sendIconColor: String? = null, val sendArrowIconColor: String? = null, val sendArrowBackgroundColor: String? = null, val sendArrowBackgroundGradient: ConciergeGradientColors? = null, val micIconColor: String? = null, val micIconGradient: ConciergeGradientColors? = null, val micRecordingIconColor: String? = null, val micWaveformGradient: ConciergeGradientColors? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:233` |
| MUI-0498 | `ConciergeGradientColors` | `ConciergeGradientColors(val startColor: String? = null, val endColor: String? = null, val angle: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:255` |
| MUI-0499 | `ConciergeFeedbackColors` | `ConciergeFeedbackColors(val iconButtonBackground: String? = null, val iconButtonHoverBackground: String? = null, val sheetBackground: String? = null, val titleText: String? = null, val questionText: String? = null, val optionsText: String? = null, val checkboxBorder: String? = null, val dragHandle: String? = null, val submitButtonFill: String? = null, val submitButtonText: String? = null, val cancelButtonFill: String? = null, val cancelButtonText: String? = null, val cancelButtonBorder: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:262` |
| MUI-0500 | `ConciergeCitationColors` | `ConciergeCitationColors(val backgroundColor: String? = null, val textColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:289` |
| MUI-0501 | `ConciergeWelcomePromptColors` | `ConciergeWelcomePromptColors(val backgroundColor: String? = null, val textColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:294` |
| MUI-0502 | `ConciergeCtaButtonColors` | `ConciergeCtaButtonColors(val backgroundColor: String? = null, val textColor: String? = null, val iconColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:299` |
| MUI-0503 | `ConciergeProductCardCtaButtonColors` | `ConciergeProductCardCtaButtonColors(val backgroundColor: String? = null, val textColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:305` |
| MUI-0504 | `ConciergeProductCardSecondaryCtaButtonColors` | `ConciergeProductCardSecondaryCtaButtonColors(val backgroundColor: String? = null, val textColor: String? = null, val borderColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:311` |
| MUI-0505 | `ConciergeThinkingColors` | `ConciergeThinkingColors(val dotColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:317` |
| MUI-0506 | `ConciergeThemeStyles` | `ConciergeThemeStyles(val header: ConciergeHeaderStyle? = null, val inputPanel: ConciergeInputPanelStyle? = null, val messageBubble: ConciergeMessageBubbleStyle? = null, val thinkingAnimation: ConciergeThinkingAnimationStyle? = null, val productCard: ConciergeProductCardStyle? = null, val productImage: ConciergeProductImageStyle? = null, val productCarousel: ConciergeProductCarouselStyle? = null, val productActionButtons: ConciergeProductActionButtonsStyle? = null, val promptSuggestions: ConciergePromptSuggestionsStyle? = null, val citation: ConciergeCitationStyle? = null, val chatFooter: ConciergeChatFooterStyle? = null, val feedbackButtons: ConciergeFeedbackButtonsStyle? = null, val errorOverlay: ConciergeErrorOverlayStyle? = null, val micButton: ConciergeMicButtonStyle? = null, val sendButton: ConciergeSendButtonStyle? = null, val messageList: ConciergeMessageListStyle? = null, val chatScreen: ConciergeChatScreenStyle? = null, val chatTextField: ConciergeChatTextFieldStyle? = null, val chatInputField: ConciergeChatInputFieldStyle? = null, val feedbackDialog: ConciergeFeedbackDialogStyle? = null, val snackbar: ConciergeSnackbarStyle? = null, val welcomeCard: ConciergeWelcomeCardStyle? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:324` |
| MUI-0507 | `ConciergeHeaderStyle` | `ConciergeHeaderStyle(val padding: Double? = null, val titleFontWeight: String? = null, val iconSize: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:350` |
| MUI-0508 | `ConciergeInputPanelStyle` | `ConciergeInputPanelStyle(val outerCornerRadius: Double? = null, val innerCornerRadius: Double? = null, val outerPadding: Double? = null, val innerPadding: Double? = null, val recordingBorderAnimationDuration: Int? = null, val buttonSpacing: Double? = null, val placeholderText: String? = null, val listeningPlaceholderText: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:356` |
| MUI-0509 | `ConciergeMessageBubbleStyle` | `ConciergeMessageBubbleStyle(val padding: Double? = null, val innerPadding: Double? = null, val cornerRadius: Double? = null, val elevation: Double? = null, val contentSpacing: Double? = null, val segmentSpacing: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:367` |
| MUI-0510 | `ConciergeThinkingAnimationStyle` | `ConciergeThinkingAnimationStyle(val dotSize: Double? = null, val dotSpacing: Double? = null, val textDotSpacing: Double? = null, val dotColorAlpha: Double? = null, val dotAnimationDuration: Int? = null, val dotAnimationDelay: Int? = null, val thinkingText: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:376` |
| MUI-0511 | `ConciergeProductCardStyle` | `ConciergeProductCardStyle(val cornerRadius: Double? = null, val elevation: Double? = null, val imageHeight: Double? = null, val titleFontWeight: String? = null, val titleMaxLines: Int? = null, val captionTopPadding: Double? = null, val captionBottomPadding: Double? = null, val textTopPadding: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:386` |
| MUI-0512 | `ConciergeProductImageStyle` | `ConciergeProductImageStyle(val singleImageCornerRadius: Double? = null, val multiImageCornerRadius: Double? = null, val elevation: Double? = null, val overlayCornerRadius: Double? = null, val overlayPadding: Double? = null, val overlayInnerPadding: Double? = null, val overlayTextSize: Double? = null, val overlayTextFontWeight: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:397` |
| MUI-0513 | `ConciergeProductCarouselStyle` | `ConciergeProductCarouselStyle(val itemSpacing: Double? = null, val horizontalPadding: Double? = null, val verticalPadding: Double? = null, val imageWidth: Double? = null, val imageHeight: Double? = null, val indicatorSize: Double? = null, val indicatorSpacing: Double? = null, val indicatorInactiveAlpha: Double? = null, val navigationIconInactiveAlpha: Double? = null, val navigationSpacing: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:408` |
| MUI-0514 | `ConciergeProductActionButtonsStyle` | `ConciergeProductActionButtonsStyle(val height: Double? = null, val cornerRadius: Double? = null, val spacing: Double? = null, val secondaryBorderWidth: Double? = null, val secondaryBorderAlpha: Double? = null, val fontSize: Double? = null, val fontWeight: String? = null, val maxLines: Int? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:421` |
| MUI-0515 | `ConciergePromptSuggestionsStyle` | `ConciergePromptSuggestionsStyle(val containerTopPadding: Double? = null, val containerStartPadding: Double? = null, val containerEndPadding: Double? = null, val itemSpacing: Double? = null, val itemCornerRadius: Double? = null, val itemHorizontalPadding: Double? = null, val itemVerticalPadding: Double? = null, val iconSize: Double? = null, val iconSpacing: Double? = null, val textMaxLines: Int? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:432` |
| MUI-0516 | `ConciergeCitationStyle` | `ConciergeCitationStyle(val containerPadding: Double? = null, val separatorHeight: Double? = null, val textLength: Int? = null, val expandAnimationDuration: Int? = null, val collapseAnimationDuration: Int? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:445` |
| MUI-0517 | `ConciergeChatFooterStyle` | `ConciergeChatFooterStyle(val sourcesButtonPadding: Double? = null, val iconSpacing: Double? = null, val sourcesText: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:453` |
| MUI-0518 | `ConciergeFeedbackButtonsStyle` | `ConciergeFeedbackButtonsStyle(val buttonSize: Double? = null, val iconSize: Double? = null, val spacing: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:459` |
| MUI-0519 | `ConciergeErrorOverlayStyle` | `ConciergeErrorOverlayStyle(val padding: Double? = null, val contentPadding: Double? = null, val dismissStartPadding: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:465` |
| MUI-0520 | `ConciergeMicButtonStyle` | `ConciergeMicButtonStyle(val size: Double? = null, val pulsingBackgroundAlpha: Double? = null, val pulseAnimationDuration: Int? = null, val pulseScaleMin: Double? = null, val pulseScaleMax: Double? = null, val ringAlpha: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:471` |
| MUI-0521 | `ConciergeSendButtonStyle` | `ConciergeSendButtonStyle(val size: Double? = null, val disabledIconAlpha: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:480` |
| MUI-0522 | `ConciergeMessageListStyle` | `ConciergeMessageListStyle(val verticalSpacing: Double? = null, val horizontalPadding: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:485` |
| MUI-0523 | `ConciergeChatScreenStyle` | `ConciergeChatScreenStyle(val backgroundColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:490` |
| MUI-0524 | `ConciergeChatTextFieldStyle` | `ConciergeChatTextFieldStyle(val horizontalPadding: Double? = null, val maxLines: Int? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:494` |
| MUI-0525 | `ConciergeChatInputFieldStyle` | `ConciergeChatInputFieldStyle(val padding: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:499` |
| MUI-0526 | `ConciergeFeedbackDialogStyle` | `ConciergeFeedbackDialogStyle(val padding: Double? = null, val elevation: Double? = null, val cornerRadius: Double? = null, val contentPadding: Double? = null, val titleSpacing: Double? = null, val questionSpacing: Double? = null, val categorySpacing: Double? = null, val checkboxSpacing: Double? = null, val categoriesNotesSpacing: Double? = null, val notesLabelSpacing: Double? = null, val notesButtonsSpacing: Double? = null, val buttonSpacing: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:503` |
| MUI-0527 | `ConciergeSnackbarStyle` | `ConciergeSnackbarStyle(val containerColor: String? = null, val contentColor: String? = null, val actionColor: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:518` |
| MUI-0528 | `ConciergeWelcomeCardStyle` | `ConciergeWelcomeCardStyle(val cornerRadius: Double? = null, val elevation: Double? = null, val contentPadding: Double? = null, val titleBottomSpacing: Double? = null, val promptsTopSpacing: Double? = null, val promptsHeaderBottomSpacing: Double? = null, val promptsSpacing: Double? = null, val promptCornerRadius: Double? = null, val promptPadding: Double? = null, val promptImageSize: Double? = null, val promptImageCornerRadius: Double? = null, val promptImageSpacing: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:524` |
| MUI-0529 | `ConciergeThemeConfig` | `val name: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:25` |
| MUI-0530 | `ConciergeThemeConfig` | `val colors: ConciergeThemeColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:26` |
| MUI-0531 | `ConciergeThemeConfig` | `val styles: ConciergeThemeStyles?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:27` |
| MUI-0532 | `ConciergeThemeConfig` | `val header: HeaderConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:28` |
| MUI-0533 | `ConciergeThemeConfig` | `val text: ConciergeTextStrings?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:29` |
| MUI-0534 | `ConciergeThemeConfig` | `val disclaimer: DisclaimerConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:30` |
| MUI-0535 | `ConciergeThemeConfig` | `val welcomeExamples: List<ConciergeWelcomeExample>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:31` |
| MUI-0536 | `ConciergeThemeConfig` | `val feedbackPositiveOptions: List<String>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:32` |
| MUI-0537 | `ConciergeThemeConfig` | `val feedbackNegativeOptions: List<String>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:33` |
| MUI-0538 | `ConciergeThemeConfig` | `val typography: ConciergeTypographyConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:34` |
| MUI-0539 | `HeaderConfig` | `val title: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:41` |
| MUI-0540 | `HeaderConfig` | `val subtitle: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:42` |
| MUI-0541 | `HeaderConfig` | `val image: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:44` |
| MUI-0542 | `HeaderConfig` | `val layoutType: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:51` |
| MUI-0543 | `HeaderConfig` | `val imageHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:56` |
| MUI-0544 | `ConciergeTypographyConfig` | `val inputFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:63` |
| MUI-0545 | `ConciergeTypographyConfig` | `val disclaimerFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:64` |
| MUI-0546 | `ConciergeTypographyConfig` | `val disclaimerFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:65` |
| MUI-0547 | `ConciergeTypographyConfig` | `val citationsFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:66` |
| MUI-0548 | `ConciergeWelcomeExample` | `val text: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:73` |
| MUI-0549 | `ConciergeWelcomeExample` | `val image: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:74` |
| MUI-0550 | `ConciergeWelcomeExample` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:75` |
| MUI-0551 | `ConciergeTextStrings` | `val inputPlaceholder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:105` |
| MUI-0552 | `ConciergeTextStrings` | `val inputAiChatIconTooltip: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:106` |
| MUI-0553 | `ConciergeTextStrings` | `val welcomeHeading: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:109` |
| MUI-0554 | `ConciergeTextStrings` | `val welcomeSubheading: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:110` |
| MUI-0555 | `ConciergeTextStrings` | `val loadingMessage: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:113` |
| MUI-0556 | `ConciergeTextStrings` | `val feedbackDialogTitlePositive: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:116` |
| MUI-0557 | `ConciergeTextStrings` | `val feedbackDialogTitleNegative: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:117` |
| MUI-0558 | `ConciergeTextStrings` | `val feedbackDialogQuestionPositive: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:118` |
| MUI-0559 | `ConciergeTextStrings` | `val feedbackDialogQuestionNegative: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:119` |
| MUI-0560 | `ConciergeTextStrings` | `val feedbackDialogNotes: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:120` |
| MUI-0561 | `ConciergeTextStrings` | `val feedbackDialogSubmit: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:121` |
| MUI-0562 | `ConciergeTextStrings` | `val feedbackDialogCancel: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:122` |
| MUI-0563 | `ConciergeTextStrings` | `val feedbackDialogNotesPlaceholder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:123` |
| MUI-0564 | `ConciergeTextStrings` | `val feedbackToastSuccess: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:124` |
| MUI-0565 | `ConciergeTextStrings` | `val feedbackHelpfulLabel: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:125` |
| MUI-0566 | `ConciergeTextStrings` | `val sourcesLabel: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:126` |
| MUI-0567 | `ConciergeTextStrings` | `val suggestionsHeader: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:127` |
| MUI-0568 | `ConciergeTextStrings` | `val errorNetwork: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:130` |
| MUI-0569 | `DisclaimerConfig` | `val text: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:137` |
| MUI-0570 | `DisclaimerConfig` | `val links: List<DisclaimerLink>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:138` |
| MUI-0571 | `PrivacyNoticeConfig` | `val title: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:148` |
| MUI-0572 | `PrivacyNoticeConfig` | `val text: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:149` |
| MUI-0573 | `PrivacyNoticeConfig` | `val links: List<DisclaimerLink>` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:150` |
| MUI-0574 | `PrivacyNoticeConfig` | `val hasContent: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:152` |
| MUI-0575 | `DisclaimerLink` | `val text: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:157` |
| MUI-0576 | `DisclaimerLink` | `val url: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:158` |
| MUI-0577 | `ConciergeThemeColors` | `val primary: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:167` |
| MUI-0578 | `ConciergeThemeColors` | `val onPrimary: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:168` |
| MUI-0579 | `ConciergeThemeColors` | `val secondary: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:169` |
| MUI-0580 | `ConciergeThemeColors` | `val surface: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:170` |
| MUI-0581 | `ConciergeThemeColors` | `val onSurface: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:171` |
| MUI-0582 | `ConciergeThemeColors` | `val onSurfaceVariant: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:172` |
| MUI-0583 | `ConciergeThemeColors` | `val background: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:173` |
| MUI-0584 | `ConciergeThemeColors` | `val container: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:174` |
| MUI-0585 | `ConciergeThemeColors` | `val outline: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:175` |
| MUI-0586 | `ConciergeThemeColors` | `val error: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:176` |
| MUI-0587 | `ConciergeThemeColors` | `val onError: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:177` |
| MUI-0588 | `ConciergeThemeColors` | `val disclaimer: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:178` |
| MUI-0589 | `ConciergeThemeColors` | `val primaryColors: ConciergePrimaryColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:181` |
| MUI-0590 | `ConciergeThemeColors` | `val surfaceColors: ConciergeSurfaceColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:182` |
| MUI-0591 | `ConciergeThemeColors` | `val message: ConciergeMessageColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:183` |
| MUI-0592 | `ConciergeThemeColors` | `val button: ConciergeButtonColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:184` |
| MUI-0593 | `ConciergeThemeColors` | `val input: ConciergeInputColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:185` |
| MUI-0594 | `ConciergeThemeColors` | `val feedback: ConciergeFeedbackColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:186` |
| MUI-0595 | `ConciergeThemeColors` | `val citation: ConciergeCitationColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:187` |
| MUI-0596 | `ConciergeThemeColors` | `val welcomePrompt: ConciergeWelcomePromptColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:188` |
| MUI-0597 | `ConciergeThemeColors` | `val ctaButton: ConciergeCtaButtonColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:189` |
| MUI-0598 | `ConciergeThemeColors` | `val productCardCtaButton: ConciergeProductCardCtaButtonColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:190` |
| MUI-0599 | `ConciergeThemeColors` | `val productCardSecondaryCtaButton: ConciergeProductCardSecondaryCtaButtonColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:191` |
| MUI-0600 | `ConciergeThemeColors` | `val promptSuggestion: ConciergeWelcomePromptColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:192` |
| MUI-0601 | `ConciergeThemeColors` | `val thinking: ConciergeThinkingColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:193` |
| MUI-0602 | `ConciergePrimaryColors` | `val primary: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:200` |
| MUI-0603 | `ConciergePrimaryColors` | `val text: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:201` |
| MUI-0604 | `ConciergeSurfaceColors` | `val mainContainerBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:205` |
| MUI-0605 | `ConciergeSurfaceColors` | `val mainContainerBottomBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:206` |
| MUI-0606 | `ConciergeSurfaceColors` | `val messageBlockerBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:207` |
| MUI-0607 | `ConciergeMessageColors` | `val userBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:211` |
| MUI-0608 | `ConciergeMessageColors` | `val userText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:212` |
| MUI-0609 | `ConciergeMessageColors` | `val conciergeBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:213` |
| MUI-0610 | `ConciergeMessageColors` | `val conciergeText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:214` |
| MUI-0611 | `ConciergeMessageColors` | `val conciergeLink: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:215` |
| MUI-0612 | `ConciergeButtonColors` | `val primaryBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:219` |
| MUI-0613 | `ConciergeButtonColors` | `val primaryText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:220` |
| MUI-0614 | `ConciergeButtonColors` | `val primaryHover: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:221` |
| MUI-0615 | `ConciergeButtonColors` | `val secondaryBorder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:222` |
| MUI-0616 | `ConciergeButtonColors` | `val secondaryText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:223` |
| MUI-0617 | `ConciergeButtonColors` | `val secondaryHover: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:224` |
| MUI-0618 | `ConciergeButtonColors` | `val secondaryHoverText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:225` |
| MUI-0619 | `ConciergeButtonColors` | `val submitFill: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:226` |
| MUI-0620 | `ConciergeButtonColors` | `val submitFillDisabled: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:227` |
| MUI-0621 | `ConciergeButtonColors` | `val submitText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:228` |
| MUI-0622 | `ConciergeButtonColors` | `val submitTextHover: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:229` |
| MUI-0623 | `ConciergeButtonColors` | `val disabledBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:230` |
| MUI-0624 | `ConciergeInputColors` | `val background: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:234` |
| MUI-0625 | `ConciergeInputColors` | `val text: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:235` |
| MUI-0626 | `ConciergeInputColors` | `val outline: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:236` |
| MUI-0627 | `ConciergeInputColors` | `val outlineGradient: ConciergeGradientColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:237` |
| MUI-0628 | `ConciergeInputColors` | `val outlineFocus: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:238` |
| MUI-0629 | `ConciergeInputColors` | `val sendIconColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:239` |
| MUI-0630 | `ConciergeInputColors` | `val sendArrowIconColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:240` |
| MUI-0631 | `ConciergeInputColors` | `val sendArrowBackgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:241` |
| MUI-0632 | `ConciergeInputColors` | `val sendArrowBackgroundGradient: ConciergeGradientColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:242` |
| MUI-0633 | `ConciergeInputColors` | `val micIconColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:243` |
| MUI-0634 | `ConciergeInputColors` | `val micIconGradient: ConciergeGradientColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:244` |
| MUI-0635 | `ConciergeInputColors` | `val micRecordingIconColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:245` |
| MUI-0636 | `ConciergeInputColors` | `val micWaveformGradient: ConciergeGradientColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:246` |
| MUI-0637 | `ConciergeGradientColors` | `val startColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:256` |
| MUI-0638 | `ConciergeGradientColors` | `val endColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:257` |
| MUI-0639 | `ConciergeGradientColors` | `val angle: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:259` |
| MUI-0640 | `ConciergeFeedbackColors` | `val iconButtonBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:263` |
| MUI-0641 | `ConciergeFeedbackColors` | `val iconButtonHoverBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:264` |
| MUI-0642 | `ConciergeFeedbackColors` | `val sheetBackground: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:266` |
| MUI-0643 | `ConciergeFeedbackColors` | `val titleText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:268` |
| MUI-0644 | `ConciergeFeedbackColors` | `val questionText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:270` |
| MUI-0645 | `ConciergeFeedbackColors` | `val optionsText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:272` |
| MUI-0646 | `ConciergeFeedbackColors` | `val checkboxBorder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:274` |
| MUI-0647 | `ConciergeFeedbackColors` | `val dragHandle: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:276` |
| MUI-0648 | `ConciergeFeedbackColors` | `val submitButtonFill: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:278` |
| MUI-0649 | `ConciergeFeedbackColors` | `val submitButtonText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:280` |
| MUI-0650 | `ConciergeFeedbackColors` | `val cancelButtonFill: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:282` |
| MUI-0651 | `ConciergeFeedbackColors` | `val cancelButtonText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:284` |
| MUI-0652 | `ConciergeFeedbackColors` | `val cancelButtonBorder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:286` |
| MUI-0653 | `ConciergeCitationColors` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:290` |
| MUI-0654 | `ConciergeCitationColors` | `val textColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:291` |
| MUI-0655 | `ConciergeWelcomePromptColors` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:295` |
| MUI-0656 | `ConciergeWelcomePromptColors` | `val textColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:296` |
| MUI-0657 | `ConciergeCtaButtonColors` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:300` |
| MUI-0658 | `ConciergeCtaButtonColors` | `val textColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:301` |
| MUI-0659 | `ConciergeCtaButtonColors` | `val iconColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:302` |
| MUI-0660 | `ConciergeProductCardCtaButtonColors` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:306` |
| MUI-0661 | `ConciergeProductCardCtaButtonColors` | `val textColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:307` |
| MUI-0662 | `ConciergeProductCardSecondaryCtaButtonColors` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:312` |
| MUI-0663 | `ConciergeProductCardSecondaryCtaButtonColors` | `val textColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:313` |
| MUI-0664 | `ConciergeProductCardSecondaryCtaButtonColors` | `val borderColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:314` |
| MUI-0665 | `ConciergeThinkingColors` | `val dotColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:318` |
| MUI-0666 | `ConciergeThemeStyles` | `val header: ConciergeHeaderStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:325` |
| MUI-0667 | `ConciergeThemeStyles` | `val inputPanel: ConciergeInputPanelStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:326` |
| MUI-0668 | `ConciergeThemeStyles` | `val messageBubble: ConciergeMessageBubbleStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:327` |
| MUI-0669 | `ConciergeThemeStyles` | `val thinkingAnimation: ConciergeThinkingAnimationStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:328` |
| MUI-0670 | `ConciergeThemeStyles` | `val productCard: ConciergeProductCardStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:329` |
| MUI-0671 | `ConciergeThemeStyles` | `val productImage: ConciergeProductImageStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:330` |
| MUI-0672 | `ConciergeThemeStyles` | `val productCarousel: ConciergeProductCarouselStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:331` |
| MUI-0673 | `ConciergeThemeStyles` | `val productActionButtons: ConciergeProductActionButtonsStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:332` |
| MUI-0674 | `ConciergeThemeStyles` | `val promptSuggestions: ConciergePromptSuggestionsStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:333` |
| MUI-0675 | `ConciergeThemeStyles` | `val citation: ConciergeCitationStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:334` |
| MUI-0676 | `ConciergeThemeStyles` | `val chatFooter: ConciergeChatFooterStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:335` |
| MUI-0677 | `ConciergeThemeStyles` | `val feedbackButtons: ConciergeFeedbackButtonsStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:336` |
| MUI-0678 | `ConciergeThemeStyles` | `val errorOverlay: ConciergeErrorOverlayStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:337` |
| MUI-0679 | `ConciergeThemeStyles` | `val micButton: ConciergeMicButtonStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:338` |
| MUI-0680 | `ConciergeThemeStyles` | `val sendButton: ConciergeSendButtonStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:339` |
| MUI-0681 | `ConciergeThemeStyles` | `val messageList: ConciergeMessageListStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:340` |
| MUI-0682 | `ConciergeThemeStyles` | `val chatScreen: ConciergeChatScreenStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:341` |
| MUI-0683 | `ConciergeThemeStyles` | `val chatTextField: ConciergeChatTextFieldStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:342` |
| MUI-0684 | `ConciergeThemeStyles` | `val chatInputField: ConciergeChatInputFieldStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:343` |
| MUI-0685 | `ConciergeThemeStyles` | `val feedbackDialog: ConciergeFeedbackDialogStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:344` |
| MUI-0686 | `ConciergeThemeStyles` | `val snackbar: ConciergeSnackbarStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:345` |
| MUI-0687 | `ConciergeThemeStyles` | `val welcomeCard: ConciergeWelcomeCardStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:346` |
| MUI-0688 | `ConciergeHeaderStyle` | `val padding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:351` |
| MUI-0689 | `ConciergeHeaderStyle` | `val titleFontWeight: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:352` |
| MUI-0690 | `ConciergeHeaderStyle` | `val iconSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:353` |
| MUI-0691 | `ConciergeInputPanelStyle` | `val outerCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:357` |
| MUI-0692 | `ConciergeInputPanelStyle` | `val innerCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:358` |
| MUI-0693 | `ConciergeInputPanelStyle` | `val outerPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:359` |
| MUI-0694 | `ConciergeInputPanelStyle` | `val innerPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:360` |
| MUI-0695 | `ConciergeInputPanelStyle` | `val recordingBorderAnimationDuration: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:361` |
| MUI-0696 | `ConciergeInputPanelStyle` | `val buttonSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:362` |
| MUI-0697 | `ConciergeInputPanelStyle` | `val placeholderText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:363` |
| MUI-0698 | `ConciergeInputPanelStyle` | `val listeningPlaceholderText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:364` |
| MUI-0699 | `ConciergeMessageBubbleStyle` | `val padding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:368` |
| MUI-0700 | `ConciergeMessageBubbleStyle` | `val innerPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:369` |
| MUI-0701 | `ConciergeMessageBubbleStyle` | `val cornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:370` |
| MUI-0702 | `ConciergeMessageBubbleStyle` | `val elevation: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:371` |
| MUI-0703 | `ConciergeMessageBubbleStyle` | `val contentSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:372` |
| MUI-0704 | `ConciergeMessageBubbleStyle` | `val segmentSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:373` |
| MUI-0705 | `ConciergeThinkingAnimationStyle` | `val dotSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:377` |
| MUI-0706 | `ConciergeThinkingAnimationStyle` | `val dotSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:378` |
| MUI-0707 | `ConciergeThinkingAnimationStyle` | `val textDotSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:379` |
| MUI-0708 | `ConciergeThinkingAnimationStyle` | `val dotColorAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:380` |
| MUI-0709 | `ConciergeThinkingAnimationStyle` | `val dotAnimationDuration: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:381` |
| MUI-0710 | `ConciergeThinkingAnimationStyle` | `val dotAnimationDelay: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:382` |
| MUI-0711 | `ConciergeThinkingAnimationStyle` | `val thinkingText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:383` |
| MUI-0712 | `ConciergeProductCardStyle` | `val cornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:387` |
| MUI-0713 | `ConciergeProductCardStyle` | `val elevation: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:388` |
| MUI-0714 | `ConciergeProductCardStyle` | `val imageHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:389` |
| MUI-0715 | `ConciergeProductCardStyle` | `val titleFontWeight: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:390` |
| MUI-0716 | `ConciergeProductCardStyle` | `val titleMaxLines: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:391` |
| MUI-0717 | `ConciergeProductCardStyle` | `val captionTopPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:392` |
| MUI-0718 | `ConciergeProductCardStyle` | `val captionBottomPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:393` |
| MUI-0719 | `ConciergeProductCardStyle` | `val textTopPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:394` |
| MUI-0720 | `ConciergeProductImageStyle` | `val singleImageCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:398` |
| MUI-0721 | `ConciergeProductImageStyle` | `val multiImageCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:399` |
| MUI-0722 | `ConciergeProductImageStyle` | `val elevation: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:400` |
| MUI-0723 | `ConciergeProductImageStyle` | `val overlayCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:401` |
| MUI-0724 | `ConciergeProductImageStyle` | `val overlayPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:402` |
| MUI-0725 | `ConciergeProductImageStyle` | `val overlayInnerPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:403` |
| MUI-0726 | `ConciergeProductImageStyle` | `val overlayTextSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:404` |
| MUI-0727 | `ConciergeProductImageStyle` | `val overlayTextFontWeight: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:405` |
| MUI-0728 | `ConciergeProductCarouselStyle` | `val itemSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:409` |
| MUI-0729 | `ConciergeProductCarouselStyle` | `val horizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:410` |
| MUI-0730 | `ConciergeProductCarouselStyle` | `val verticalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:411` |
| MUI-0731 | `ConciergeProductCarouselStyle` | `val imageWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:412` |
| MUI-0732 | `ConciergeProductCarouselStyle` | `val imageHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:413` |
| MUI-0733 | `ConciergeProductCarouselStyle` | `val indicatorSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:414` |
| MUI-0734 | `ConciergeProductCarouselStyle` | `val indicatorSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:415` |
| MUI-0735 | `ConciergeProductCarouselStyle` | `val indicatorInactiveAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:416` |
| MUI-0736 | `ConciergeProductCarouselStyle` | `val navigationIconInactiveAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:417` |
| MUI-0737 | `ConciergeProductCarouselStyle` | `val navigationSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:418` |
| MUI-0738 | `ConciergeProductActionButtonsStyle` | `val height: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:422` |
| MUI-0739 | `ConciergeProductActionButtonsStyle` | `val cornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:423` |
| MUI-0740 | `ConciergeProductActionButtonsStyle` | `val spacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:424` |
| MUI-0741 | `ConciergeProductActionButtonsStyle` | `val secondaryBorderWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:425` |
| MUI-0742 | `ConciergeProductActionButtonsStyle` | `val secondaryBorderAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:426` |
| MUI-0743 | `ConciergeProductActionButtonsStyle` | `val fontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:427` |
| MUI-0744 | `ConciergeProductActionButtonsStyle` | `val fontWeight: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:428` |
| MUI-0745 | `ConciergeProductActionButtonsStyle` | `val maxLines: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:429` |
| MUI-0746 | `ConciergePromptSuggestionsStyle` | `val containerTopPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:433` |
| MUI-0747 | `ConciergePromptSuggestionsStyle` | `val containerStartPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:434` |
| MUI-0748 | `ConciergePromptSuggestionsStyle` | `val containerEndPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:435` |
| MUI-0749 | `ConciergePromptSuggestionsStyle` | `val itemSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:436` |
| MUI-0750 | `ConciergePromptSuggestionsStyle` | `val itemCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:437` |
| MUI-0751 | `ConciergePromptSuggestionsStyle` | `val itemHorizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:438` |
| MUI-0752 | `ConciergePromptSuggestionsStyle` | `val itemVerticalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:439` |
| MUI-0753 | `ConciergePromptSuggestionsStyle` | `val iconSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:440` |
| MUI-0754 | `ConciergePromptSuggestionsStyle` | `val iconSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:441` |
| MUI-0755 | `ConciergePromptSuggestionsStyle` | `val textMaxLines: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:442` |
| MUI-0756 | `ConciergeCitationStyle` | `val containerPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:446` |
| MUI-0757 | `ConciergeCitationStyle` | `val separatorHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:447` |
| MUI-0758 | `ConciergeCitationStyle` | `val textLength: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:448` |
| MUI-0759 | `ConciergeCitationStyle` | `val expandAnimationDuration: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:449` |
| MUI-0760 | `ConciergeCitationStyle` | `val collapseAnimationDuration: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:450` |
| MUI-0761 | `ConciergeChatFooterStyle` | `val sourcesButtonPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:454` |
| MUI-0762 | `ConciergeChatFooterStyle` | `val iconSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:455` |
| MUI-0763 | `ConciergeChatFooterStyle` | `val sourcesText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:456` |
| MUI-0764 | `ConciergeFeedbackButtonsStyle` | `val buttonSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:460` |
| MUI-0765 | `ConciergeFeedbackButtonsStyle` | `val iconSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:461` |
| MUI-0766 | `ConciergeFeedbackButtonsStyle` | `val spacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:462` |
| MUI-0767 | `ConciergeErrorOverlayStyle` | `val padding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:466` |
| MUI-0768 | `ConciergeErrorOverlayStyle` | `val contentPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:467` |
| MUI-0769 | `ConciergeErrorOverlayStyle` | `val dismissStartPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:468` |
| MUI-0770 | `ConciergeMicButtonStyle` | `val size: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:472` |
| MUI-0771 | `ConciergeMicButtonStyle` | `val pulsingBackgroundAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:473` |
| MUI-0772 | `ConciergeMicButtonStyle` | `val pulseAnimationDuration: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:474` |
| MUI-0773 | `ConciergeMicButtonStyle` | `val pulseScaleMin: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:475` |
| MUI-0774 | `ConciergeMicButtonStyle` | `val pulseScaleMax: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:476` |
| MUI-0775 | `ConciergeMicButtonStyle` | `val ringAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:477` |
| MUI-0776 | `ConciergeSendButtonStyle` | `val size: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:481` |
| MUI-0777 | `ConciergeSendButtonStyle` | `val disabledIconAlpha: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:482` |
| MUI-0778 | `ConciergeMessageListStyle` | `val verticalSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:486` |
| MUI-0779 | `ConciergeMessageListStyle` | `val horizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:487` |
| MUI-0780 | `ConciergeChatScreenStyle` | `val backgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:491` |
| MUI-0781 | `ConciergeChatTextFieldStyle` | `val horizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:495` |
| MUI-0782 | `ConciergeChatTextFieldStyle` | `val maxLines: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:496` |
| MUI-0783 | `ConciergeChatInputFieldStyle` | `val padding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:500` |
| MUI-0784 | `ConciergeFeedbackDialogStyle` | `val padding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:504` |
| MUI-0785 | `ConciergeFeedbackDialogStyle` | `val elevation: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:505` |
| MUI-0786 | `ConciergeFeedbackDialogStyle` | `val cornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:506` |
| MUI-0787 | `ConciergeFeedbackDialogStyle` | `val contentPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:507` |
| MUI-0788 | `ConciergeFeedbackDialogStyle` | `val titleSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:508` |
| MUI-0789 | `ConciergeFeedbackDialogStyle` | `val questionSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:509` |
| MUI-0790 | `ConciergeFeedbackDialogStyle` | `val categorySpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:510` |
| MUI-0791 | `ConciergeFeedbackDialogStyle` | `val checkboxSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:511` |
| MUI-0792 | `ConciergeFeedbackDialogStyle` | `val categoriesNotesSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:512` |
| MUI-0793 | `ConciergeFeedbackDialogStyle` | `val notesLabelSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:513` |
| MUI-0794 | `ConciergeFeedbackDialogStyle` | `val notesButtonsSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:514` |
| MUI-0795 | `ConciergeFeedbackDialogStyle` | `val buttonSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:515` |
| MUI-0796 | `ConciergeSnackbarStyle` | `val containerColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:519` |
| MUI-0797 | `ConciergeSnackbarStyle` | `val contentColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:520` |
| MUI-0798 | `ConciergeSnackbarStyle` | `val actionColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:521` |
| MUI-0799 | `ConciergeWelcomeCardStyle` | `val cornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:525` |
| MUI-0800 | `ConciergeWelcomeCardStyle` | `val elevation: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:526` |
| MUI-0801 | `ConciergeWelcomeCardStyle` | `val contentPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:527` |
| MUI-0802 | `ConciergeWelcomeCardStyle` | `val titleBottomSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:528` |
| MUI-0803 | `ConciergeWelcomeCardStyle` | `val promptsTopSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:529` |
| MUI-0804 | `ConciergeWelcomeCardStyle` | `val promptsHeaderBottomSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:530` |
| MUI-0805 | `ConciergeWelcomeCardStyle` | `val promptsSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:531` |
| MUI-0806 | `ConciergeWelcomeCardStyle` | `val promptCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:532` |
| MUI-0807 | `ConciergeWelcomeCardStyle` | `val promptPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:533` |
| MUI-0808 | `ConciergeWelcomeCardStyle` | `val promptImageSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:534` |
| MUI-0809 | `ConciergeWelcomeCardStyle` | `val promptImageCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:535` |
| MUI-0810 | `ConciergeWelcomeCardStyle` | `val promptImageSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:536` |
| MUI-0811 | `ConciergeTextAlignment` | `fun fromString(value: String?): ConciergeTextAlignment` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:267` |
| MUI-0812 | `UserMessageBubbleStyle` | `fun fromString(value: String): UserMessageBubbleStyle` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:288` |
| MUI-0813 | `FeedbackDisplayMode` | `fun fromString(value: String): FeedbackDisplayMode` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:304` |
| MUI-0814 | `ThinkingDotVerticalAlignment` | `fun fromString(value: String): ThinkingDotVerticalAlignment` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:328` |
| MUI-0815 | `ProductCardStyle` | `fun fromString(value: String): ProductCardStyle` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:342` |
| MUI-0816 | `CarouselStyle` | `fun fromString(value: String): CarouselStyle` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:356` |
| MUI-0817 | `ConciergeFeedbackBehavior` | `fun resolvedShowCloseButton(): Boolean` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:372` |
| MUI-0818 | `ConciergeFeedbackBehavior` | `fun resolvedShowCancelButton(): Boolean` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:375` |
| MUI-0819 | `FeedbackThumbsPlacement` | `fun fromString(value: String): FeedbackThumbsPlacement` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:390` |
| MUI-0820 | `CardsAlignment` | `fun fromString(value: String): CardsAlignment` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:422` |
| MUI-0821 | `ConciergeThemeTokens` | `ConciergeThemeTokens(val metadata: ConciergeThemeMetadata = ConciergeThemeMetadata(), val behavior: ConciergeThemeBehavior = ConciergeThemeBehavior(), val colors: ConciergeThemeColors? = null, val assets: ConciergeThemeAssets = ConciergeThemeAssets(), val content: ConciergeThemeContent = ConciergeThemeContent(), val layout: ConciergeThemeLayout = ConciergeThemeLayout(), val cssLayout: ConciergeLayout? = null, val typography: ConciergeTypography? = null, val components: ConciergeComponentsConfig? = null, val cssVariables: Map<String, String> = emptyMap())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:19` |
| MUI-0822 | `ConciergeTypography` | `ConciergeTypography(val fontFamily: String? = null, val lineHeight: Double? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:35` |
| MUI-0823 | `ConciergeLayout` | `ConciergeLayout(val inputHeight: Double? = null, val inputBorderRadius: Double? = null, val inputOutlineWidth: Double? = null, val inputFocusOutlineWidth: Double? = null, val inputFontSize: Double? = null, val inputButtonHeight: Double? = null, val inputButtonWidth: Double? = null, val inputButtonBorderRadius: Double? = null, val inputBoxShadow: Map<String, Any>? = null, val messageBorderRadius: Double? = null, val messagePadding: List<Double>? = null, val messageMaxWidth: Double? = null, val agentIconSize: Double? = null, val agentIconSpacing: Double? = null, val chatInterfaceMaxWidth: Double? = null, val chatHistoryPadding: Double? = null, val chatHistoryPaddingTopExpanded: Double? = null, val chatHistoryBottomPadding: Double? = null, val messageBlockerHeight: Double? = null, val borderRadiusCard: Double? = null, val multimodalCardBoxShadow: Map<String, Any>? = null, val buttonHeightSmall: Double? = null, val feedbackContainerGap: Double? = null, val feedbackSubmitButtonBorderRadius: Double? = null, val feedbackCancelButtonBorderRadius: Double? = null, val feedbackCancelButtonBorderWidth: Double? = null, val feedbackSubmitButtonFontWeight: Int? = null, val feedbackCancelButtonFontWeight: Int? = null, val feedbackCheckboxBorderRadius: Double? = null, val feedbackTitleTextAlign: ConciergeTextAlignment? = null, val feedbackTitleFontSize: Double? = null, val citationsTextFontWeight: Int? = null, val citationsDesktopButtonFontSize: Double? = null, val disclaimerFontSize: Double? = null, val disclaimerFontWeight: Int? = null, val welcomeInputOrder: Int? = null, val welcomeCardsOrder: Int? = null, val welcomeTitleFontSize: Double? = null, val welcomeTextAlign: String? = null, val welcomeContentPadding: Double? = null, val welcomePromptImageSize: Double? = null, val welcomePromptSpacing: Double? = null, val welcomeTitleBottomSpacing: Double? = null, val welcomePromptsTopSpacing: Double? = null, val welcomePromptPadding: Double? = null, val welcomePromptCornerRadius: Double? = null, val headerTitleFontSize: Double? = null, val suggestionItemBorderRadius: Double? = null, val productCardTitleFontWeight: Int? = null, val productCardTitleFontSize: Double? = null, val productCardSubtitleFontWeight: Int? = null, val productCardSubtitleFontSize: Double? = null, val productCardDescriptionMaxLines: Int = 2, val productCardPriceFontWeight: Int? = null, val productCardPriceFontSize: Double? = null, val productCardBadgeFontSize: Double? = null, val productCardBadgeFontWeight: Int? = null, val productCardBadgeTextColor: String? = null, val productCardBadgeBackgroundColor: String? = null, val productCardBackgroundColor: String? = null, val productCardTitleColor: String? = null, val productCardSubtitleColor: String? = null, val productCardPriceColor: String? = null, val productCardOutlineColor: String? = null, val productCardWidth: Double? = null, val productCardMinHeight: Double? = null, val productCardMaxHeight: Double? = null, val productImageWidth: Double? = null, val productImageHeight: Double? = null, val productImageScale: String? = null, val productCardBorderRadius: Double? = null, val productCardWasPriceColor: String? = null, val productCardWasPriceFontSize: Double? = null, val productCardWasPriceFontWeight: Int? = null, val productCardWasPriceTextPrefix: String? = null, val productCardTextSpacing: Double? = null, val productCardTitleSubtitleSpacing: Double? = null, val productCardSectionSpacing: Double? = null, val productCardPriceSpacing: Double? = null, val productCardTextTopPadding: Double? = null, val productCardTextBottomPadding: Double? = null, val productCardTextHorizontalPadding: Double? = null, val productCardCarouselHorizontalPadding: Double? = null, val productCardCarouselSpacing: Double? = null, val ctaButtonBorderRadius: Double? = null, val ctaButtonHorizontalPadding: Double? = null, val ctaButtonVerticalPadding: Double? = null, val ctaButtonFontSize: Double? = null, val ctaButtonFontWeight: Int? = null, val ctaButtonIconSize: Double? = null, val productCardCtaButtonBorderRadius: Double? = null, val productCardCtaButtonHorizontalPadding: Double? = null, val productCardCtaButtonVerticalPadding: Double? = null, val productCardCtaButtonFontSize: Double? = null, val productCardCtaButtonFontWeight: Int? = null, val productCardSecondaryCtaButtonBorderWidth: Double? = null, val thinkingDotSize: Double? = null, val thinkingDotSpacing: Double? = null, val thinkingBubbleBorderRadius: Double? = null, val thinkingBubblePaddingHorizontal: Double? = null, val thinkingBubblePaddingVertical: Double? = null, val thinkingDotVerticalAlignment: String? = null, val spacing: ConciergeSpacingLayout? = null, val sizing: ConciergeSizingLayout? = null, val positioning: ConciergePositioningLayout? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:43` |
| MUI-0824 | `ConciergeComponentsConfig` | `ConciergeComponentsConfig(val feedback: ConciergeFeedbackComponent? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:185` |
| MUI-0825 | `ConciergeFeedbackComponent` | `ConciergeFeedbackComponent(val iconButtonSizeDesktop: Double? = null, val positiveNotesEnabled: Boolean = true, val negativeNotesEnabled: Boolean = true)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:189` |
| MUI-0826 | `ConciergeThemeMetadata` | `ConciergeThemeMetadata(val version: String = "1.0.0", val name: String = "Default Theme", val description: String? = null, val author: String? = null, val lastModified: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:200` |
| MUI-0827 | `ConciergeThemeBehavior` | `ConciergeThemeBehavior(val enableDarkMode: Boolean = true, val enableAnimations: Boolean = true, val enableHaptics: Boolean = true, val enableSoundEffects: Boolean = false, val autoScrollToBottom: Boolean = true, val showTimestamps: Boolean = false, val enableMarkdown: Boolean = true, val enableCitations: Boolean = true, val welcomeCard: ConciergeWelcomeCardBehavior? = null, val enableVoiceInput: Boolean = true, val disableMultiline: Boolean = true, val sendButtonStyle: String = "default", val stopRecordingIcon: String? = null, val showAiChatIcon: String? = null, val enableMicPulseBackground: Boolean = true, val maxMessageLength: Int = 2000, val typingIndicatorDelay: Int = 500, val feedback: ConciergeFeedbackBehavior? = null, val citations: ConciergeCitationsBehavior? = null, val productCard: ConciergeProductCardBehavior? = null, val multimodalCarousel: ConciergeMultimodalCarouselBehavior? = null, val chat: ConciergeChatBehavior? = null, val promptSuggestions: ConciergePromptSuggestionsBehavior? = null, val privacyNotice: PrivacyNoticeConfig = PrivacyNoticeConfig())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:211` |
| MUI-0828 | `ConciergePromptSuggestionsBehavior` | `ConciergePromptSuggestionsBehavior(val itemMaxLines: Int = 1, val showHeader: Boolean = false)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:241` |
| MUI-0829 | `ConciergeChatBehavior` | `ConciergeChatBehavior(val messageAlignment: ConciergeTextAlignment = ConciergeTextAlignment.START, val messageWidth: String? = null, val userMessageBubbleStyle: UserMessageBubbleStyle = UserMessageBubbleStyle.DEFAULT)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:249` |
| MUI-0830 | `ConciergeFeedbackBehavior` | `ConciergeFeedbackBehavior(val displayMode: FeedbackDisplayMode = FeedbackDisplayMode.MODAL, val thumbsPlacement: FeedbackThumbsPlacement = FeedbackThumbsPlacement.INLINE, val showCloseButton: Boolean? = null, val showCancelButton: Boolean? = null, val alwaysDisplay: Boolean = false)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:361` |
| MUI-0831 | `ConciergeLinkIconStyle` | `ConciergeLinkIconStyle(val size: Float? = null, val spacing: Float? = null, val color: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:395` |
| MUI-0832 | `ConciergeCitationsBehavior` | `ConciergeCitationsBehavior(val showLinkIcon: Boolean = false, val phoneIcon: String? = null, val storeIcon: String? = null, val defaultLinkIcon: String? = null, val linkIconStyle: ConciergeLinkIconStyle? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:401` |
| MUI-0833 | `ConciergeProductCardBehavior` | `ConciergeProductCardBehavior(val cardStyle: ProductCardStyle = ProductCardStyle.ACTION_BUTTON, val cardsAlignment: CardsAlignment = CardsAlignment.CENTER)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:427` |
| MUI-0834 | `ConciergeMultimodalCarouselBehavior` | `ConciergeMultimodalCarouselBehavior(val carouselStyle: CarouselStyle = CarouselStyle.PAGED)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:432` |
| MUI-0835 | `ConciergeWelcomeCardBehavior` | `ConciergeWelcomeCardBehavior(val closeButtonAlignment: String = "end", val promptFullWidth: Boolean = true, val promptMaxLines: Int = Int.MAX_VALUE, val contentAlignment: String = "top")` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:436` |
| MUI-0836 | `ConciergeThemeAssets` | `ConciergeThemeAssets(val icons: ConciergeIconAssets = ConciergeIconAssets(), val images: ConciergeImageAssets = ConciergeImageAssets(), val fonts: ConciergeThemeFonts = ConciergeThemeFonts())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:446` |
| MUI-0837 | `ConciergeIconAssets` | `ConciergeIconAssets(val company: String? = null, val send: String? = null, val microphone: String? = null, val close: String? = null, val thumbsUp: String? = null, val thumbsDown: String? = null, val chevronDown: String? = null, val chevronRight: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:452` |
| MUI-0838 | `ConciergeImageAssets` | `ConciergeImageAssets(val welcomeBanner: String? = null, val errorPlaceholder: String? = null, val avatarBot: String? = null, val avatarUser: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:463` |
| MUI-0839 | `ConciergeThemeFonts` | `ConciergeThemeFonts(val regular: String? = null, val medium: String? = null, val bold: String? = null, val light: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:470` |
| MUI-0840 | `ConciergeThemeContent` | `ConciergeThemeContent(val text: ConciergeTextContent = ConciergeTextContent(), val placeholders: ConciergePlaceholderContent = ConciergePlaceholderContent(), val accessibility: ConciergeAccessibilityContent = ConciergeAccessibilityContent())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:480` |
| MUI-0841 | `ConciergeTextContent` | `ConciergeTextContent(val welcomeTitle: String = "How can I help you?", val welcomeSubtitle: String? = null, val disclaimerText: String? = null, val errorTitle: String = "Something went wrong", val errorRetry: String = "Try again", val feedbackTitle: String = "Provide feedback", val feedbackSubmit: String = "Submit", val feedbackCancel: String = "Cancel", val thinkingLabel: String = "Thinking", val listeningLabel: String = "Listening")` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:486` |
| MUI-0842 | `ConciergePlaceholderContent` | `ConciergePlaceholderContent(val inputPlaceholder: String = "How can I help", val listeningPlaceholder: String = "Listening...", val emptyStateMessage: String? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:499` |
| MUI-0843 | `ConciergeAccessibilityContent` | `ConciergeAccessibilityContent(val sendButtonLabel: String = "Send message", val micButtonLabel: String = "Voice input", val closeButtonLabel: String = "Close", val thumbsUpLabel: String = "Like this response", val thumbsDownLabel: String = "Dislike this response")` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:505` |
| MUI-0844 | `ConciergeThemeLayout` | `ConciergeThemeLayout(val spacing: ConciergeSpacingLayout = ConciergeSpacingLayout(), val sizing: ConciergeSizingLayout = ConciergeSizingLayout(), val positioning: ConciergePositioningLayout = ConciergePositioningLayout())` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:516` |
| MUI-0845 | `ConciergeSpacingLayout` | `ConciergeSpacingLayout(val xs: Double = 4.0, val sm: Double = 8.0, val md: Double = 16.0, val lg: Double = 24.0, val xl: Double = 32.0, val xxl: Double = 48.0)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:522` |
| MUI-0846 | `ConciergeSizingLayout` | `ConciergeSizingLayout(val iconSm: Double = 16.0, val iconMd: Double = 24.0, val iconLg: Double = 32.0, val avatarSm: Double = 32.0, val avatarMd: Double = 40.0, val avatarLg: Double = 48.0, val buttonHeightSm: Double = 32.0, val buttonHeightMd: Double = 40.0, val buttonHeightLg: Double = 48.0)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:531` |
| MUI-0847 | `ConciergePositioningLayout` | `ConciergePositioningLayout(val headerHeight: Double = 56.0, val footerHeight: Double = 72.0, val maxContentWidth: Double = 800.0, val minContentWidth: Double = 280.0)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:543` |
| MUI-0848 | `ConciergeThemeTokens` | `val metadata: ConciergeThemeMetadata` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:20` |
| MUI-0849 | `ConciergeThemeTokens` | `val behavior: ConciergeThemeBehavior` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:21` |
| MUI-0850 | `ConciergeThemeTokens` | `val colors: ConciergeThemeColors?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:22` |
| MUI-0851 | `ConciergeThemeTokens` | `val assets: ConciergeThemeAssets` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:23` |
| MUI-0852 | `ConciergeThemeTokens` | `val content: ConciergeThemeContent` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:24` |
| MUI-0853 | `ConciergeThemeTokens` | `val layout: ConciergeThemeLayout` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:25` |
| MUI-0854 | `ConciergeThemeTokens` | `val cssLayout: ConciergeLayout?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:26` |
| MUI-0855 | `ConciergeThemeTokens` | `val typography: ConciergeTypography?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:27` |
| MUI-0856 | `ConciergeThemeTokens` | `val components: ConciergeComponentsConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:28` |
| MUI-0857 | `ConciergeThemeTokens` | `val cssVariables: Map<String, String>` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:29` |
| MUI-0858 | `ConciergeTypography` | `val fontFamily: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:36` |
| MUI-0859 | `ConciergeTypography` | `val lineHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:37` |
| MUI-0860 | `ConciergeLayout` | `val inputHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:45` |
| MUI-0861 | `ConciergeLayout` | `val inputBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:46` |
| MUI-0862 | `ConciergeLayout` | `val inputOutlineWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:47` |
| MUI-0863 | `ConciergeLayout` | `val inputFocusOutlineWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:48` |
| MUI-0864 | `ConciergeLayout` | `val inputFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:49` |
| MUI-0865 | `ConciergeLayout` | `val inputButtonHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:50` |
| MUI-0866 | `ConciergeLayout` | `val inputButtonWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:51` |
| MUI-0867 | `ConciergeLayout` | `val inputButtonBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:52` |
| MUI-0868 | `ConciergeLayout` | `val inputBoxShadow: Map<String, Any>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:53` |
| MUI-0869 | `ConciergeLayout` | `val messageBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:56` |
| MUI-0870 | `ConciergeLayout` | `val messagePadding: List<Double>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:57` |
| MUI-0871 | `ConciergeLayout` | `val messageMaxWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:58` |
| MUI-0872 | `ConciergeLayout` | `val agentIconSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:59` |
| MUI-0873 | `ConciergeLayout` | `val agentIconSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:60` |
| MUI-0874 | `ConciergeLayout` | `val chatInterfaceMaxWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:63` |
| MUI-0875 | `ConciergeLayout` | `val chatHistoryPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:64` |
| MUI-0876 | `ConciergeLayout` | `val chatHistoryPaddingTopExpanded: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:65` |
| MUI-0877 | `ConciergeLayout` | `val chatHistoryBottomPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:66` |
| MUI-0878 | `ConciergeLayout` | `val messageBlockerHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:67` |
| MUI-0879 | `ConciergeLayout` | `val borderRadiusCard: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:70` |
| MUI-0880 | `ConciergeLayout` | `val multimodalCardBoxShadow: Map<String, Any>?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:71` |
| MUI-0881 | `ConciergeLayout` | `val buttonHeightSmall: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:74` |
| MUI-0882 | `ConciergeLayout` | `val feedbackContainerGap: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:77` |
| MUI-0883 | `ConciergeLayout` | `val feedbackSubmitButtonBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:78` |
| MUI-0884 | `ConciergeLayout` | `val feedbackCancelButtonBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:79` |
| MUI-0885 | `ConciergeLayout` | `val feedbackCancelButtonBorderWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:80` |
| MUI-0886 | `ConciergeLayout` | `val feedbackSubmitButtonFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:81` |
| MUI-0887 | `ConciergeLayout` | `val feedbackCancelButtonFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:82` |
| MUI-0888 | `ConciergeLayout` | `val feedbackCheckboxBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:83` |
| MUI-0889 | `ConciergeLayout` | `val feedbackTitleTextAlign: ConciergeTextAlignment?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:85` |
| MUI-0890 | `ConciergeLayout` | `val feedbackTitleFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:87` |
| MUI-0891 | `ConciergeLayout` | `val citationsTextFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:90` |
| MUI-0892 | `ConciergeLayout` | `val citationsDesktopButtonFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:91` |
| MUI-0893 | `ConciergeLayout` | `val disclaimerFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:94` |
| MUI-0894 | `ConciergeLayout` | `val disclaimerFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:95` |
| MUI-0895 | `ConciergeLayout` | `val welcomeInputOrder: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:98` |
| MUI-0896 | `ConciergeLayout` | `val welcomeCardsOrder: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:99` |
| MUI-0897 | `ConciergeLayout` | `val welcomeTitleFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:100` |
| MUI-0898 | `ConciergeLayout` | `val welcomeTextAlign: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:101` |
| MUI-0899 | `ConciergeLayout` | `val welcomeContentPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:102` |
| MUI-0900 | `ConciergeLayout` | `val welcomePromptImageSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:103` |
| MUI-0901 | `ConciergeLayout` | `val welcomePromptSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:104` |
| MUI-0902 | `ConciergeLayout` | `val welcomeTitleBottomSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:105` |
| MUI-0903 | `ConciergeLayout` | `val welcomePromptsTopSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:106` |
| MUI-0904 | `ConciergeLayout` | `val welcomePromptPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:107` |
| MUI-0905 | `ConciergeLayout` | `val welcomePromptCornerRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:108` |
| MUI-0906 | `ConciergeLayout` | `val headerTitleFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:109` |
| MUI-0907 | `ConciergeLayout` | `val suggestionItemBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:110` |
| MUI-0908 | `ConciergeLayout` | `val productCardTitleFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:113` |
| MUI-0909 | `ConciergeLayout` | `val productCardTitleFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:114` |
| MUI-0910 | `ConciergeLayout` | `val productCardSubtitleFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:115` |
| MUI-0911 | `ConciergeLayout` | `val productCardSubtitleFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:116` |
| MUI-0912 | `ConciergeLayout` | `val productCardDescriptionMaxLines: Int` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:118` |
| MUI-0913 | `ConciergeLayout` | `val productCardPriceFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:119` |
| MUI-0914 | `ConciergeLayout` | `val productCardPriceFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:120` |
| MUI-0915 | `ConciergeLayout` | `val productCardBadgeFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:121` |
| MUI-0916 | `ConciergeLayout` | `val productCardBadgeFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:122` |
| MUI-0917 | `ConciergeLayout` | `val productCardBadgeTextColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:123` |
| MUI-0918 | `ConciergeLayout` | `val productCardBadgeBackgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:124` |
| MUI-0919 | `ConciergeLayout` | `val productCardBackgroundColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:125` |
| MUI-0920 | `ConciergeLayout` | `val productCardTitleColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:126` |
| MUI-0921 | `ConciergeLayout` | `val productCardSubtitleColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:127` |
| MUI-0922 | `ConciergeLayout` | `val productCardPriceColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:128` |
| MUI-0923 | `ConciergeLayout` | `val productCardOutlineColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:129` |
| MUI-0924 | `ConciergeLayout` | `val productCardWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:130` |
| MUI-0925 | `ConciergeLayout` | `val productCardMinHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:131` |
| MUI-0926 | `ConciergeLayout` | `val productCardMaxHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:132` |
| MUI-0927 | `ConciergeLayout` | `val productImageWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:133` |
| MUI-0928 | `ConciergeLayout` | `val productImageHeight: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:134` |
| MUI-0929 | `ConciergeLayout` | `val productImageScale: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:135` |
| MUI-0930 | `ConciergeLayout` | `val productCardBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:136` |
| MUI-0931 | `ConciergeLayout` | `val productCardWasPriceColor: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:137` |
| MUI-0932 | `ConciergeLayout` | `val productCardWasPriceFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:138` |
| MUI-0933 | `ConciergeLayout` | `val productCardWasPriceFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:139` |
| MUI-0934 | `ConciergeLayout` | `val productCardWasPriceTextPrefix: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:140` |
| MUI-0935 | `ConciergeLayout` | `val productCardTextSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:141` |
| MUI-0936 | `ConciergeLayout` | `val productCardTitleSubtitleSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:142` |
| MUI-0937 | `ConciergeLayout` | `val productCardSectionSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:143` |
| MUI-0938 | `ConciergeLayout` | `val productCardPriceSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:144` |
| MUI-0939 | `ConciergeLayout` | `val productCardTextTopPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:145` |
| MUI-0940 | `ConciergeLayout` | `val productCardTextBottomPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:146` |
| MUI-0941 | `ConciergeLayout` | `val productCardTextHorizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:147` |
| MUI-0942 | `ConciergeLayout` | `val productCardCarouselHorizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:148` |
| MUI-0943 | `ConciergeLayout` | `val productCardCarouselSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:149` |
| MUI-0944 | `ConciergeLayout` | `val ctaButtonBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:152` |
| MUI-0945 | `ConciergeLayout` | `val ctaButtonHorizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:153` |
| MUI-0946 | `ConciergeLayout` | `val ctaButtonVerticalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:154` |
| MUI-0947 | `ConciergeLayout` | `val ctaButtonFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:155` |
| MUI-0948 | `ConciergeLayout` | `val ctaButtonFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:156` |
| MUI-0949 | `ConciergeLayout` | `val ctaButtonIconSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:157` |
| MUI-0950 | `ConciergeLayout` | `val productCardCtaButtonBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:160` |
| MUI-0951 | `ConciergeLayout` | `val productCardCtaButtonHorizontalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:161` |
| MUI-0952 | `ConciergeLayout` | `val productCardCtaButtonVerticalPadding: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:162` |
| MUI-0953 | `ConciergeLayout` | `val productCardCtaButtonFontSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:163` |
| MUI-0954 | `ConciergeLayout` | `val productCardCtaButtonFontWeight: Int?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:164` |
| MUI-0955 | `ConciergeLayout` | `val productCardSecondaryCtaButtonBorderWidth: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:166` |
| MUI-0956 | `ConciergeLayout` | `val thinkingDotSize: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:169` |
| MUI-0957 | `ConciergeLayout` | `val thinkingDotSpacing: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:170` |
| MUI-0958 | `ConciergeLayout` | `val thinkingBubbleBorderRadius: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:171` |
| MUI-0959 | `ConciergeLayout` | `val thinkingBubblePaddingHorizontal: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:172` |
| MUI-0960 | `ConciergeLayout` | `val thinkingBubblePaddingVertical: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:173` |
| MUI-0961 | `ConciergeLayout` | `val thinkingDotVerticalAlignment: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:174` |
| MUI-0962 | `ConciergeLayout` | `val spacing: ConciergeSpacingLayout?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:177` |
| MUI-0963 | `ConciergeLayout` | `val sizing: ConciergeSizingLayout?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:178` |
| MUI-0964 | `ConciergeLayout` | `val positioning: ConciergePositioningLayout?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:179` |
| MUI-0965 | `ConciergeComponentsConfig` | `val feedback: ConciergeFeedbackComponent?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:186` |
| MUI-0966 | `ConciergeFeedbackComponent` | `val iconButtonSizeDesktop: Double?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:190` |
| MUI-0967 | `ConciergeFeedbackComponent` | `val positiveNotesEnabled: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:192` |
| MUI-0968 | `ConciergeFeedbackComponent` | `val negativeNotesEnabled: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:194` |
| MUI-0969 | `ConciergeThemeMetadata` | `val version: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:201` |
| MUI-0970 | `ConciergeThemeMetadata` | `val name: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:202` |
| MUI-0971 | `ConciergeThemeMetadata` | `val description: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:203` |
| MUI-0972 | `ConciergeThemeMetadata` | `val author: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:204` |
| MUI-0973 | `ConciergeThemeMetadata` | `val lastModified: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:205` |
| MUI-0974 | `ConciergeThemeBehavior` | `val enableDarkMode: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:212` |
| MUI-0975 | `ConciergeThemeBehavior` | `val enableAnimations: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:213` |
| MUI-0976 | `ConciergeThemeBehavior` | `val enableHaptics: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:214` |
| MUI-0977 | `ConciergeThemeBehavior` | `val enableSoundEffects: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:215` |
| MUI-0978 | `ConciergeThemeBehavior` | `val autoScrollToBottom: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:216` |
| MUI-0979 | `ConciergeThemeBehavior` | `val showTimestamps: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:217` |
| MUI-0980 | `ConciergeThemeBehavior` | `val enableMarkdown: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:218` |
| MUI-0981 | `ConciergeThemeBehavior` | `val enableCitations: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:219` |
| MUI-0982 | `ConciergeThemeBehavior` | `val welcomeCard: ConciergeWelcomeCardBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:220` |
| MUI-0983 | `ConciergeThemeBehavior` | `val enableVoiceInput: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:221` |
| MUI-0984 | `ConciergeThemeBehavior` | `val disableMultiline: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:222` |
| MUI-0985 | `ConciergeThemeBehavior` | `val sendButtonStyle: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:223` |
| MUI-0986 | `ConciergeThemeBehavior` | `val stopRecordingIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:225` |
| MUI-0987 | `ConciergeThemeBehavior` | `val showAiChatIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:227` |
| MUI-0988 | `ConciergeThemeBehavior` | `val enableMicPulseBackground: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:229` |
| MUI-0989 | `ConciergeThemeBehavior` | `val maxMessageLength: Int` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:230` |
| MUI-0990 | `ConciergeThemeBehavior` | `val typingIndicatorDelay: Int` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:231` |
| MUI-0991 | `ConciergeThemeBehavior` | `val feedback: ConciergeFeedbackBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:232` |
| MUI-0992 | `ConciergeThemeBehavior` | `val citations: ConciergeCitationsBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:233` |
| MUI-0993 | `ConciergeThemeBehavior` | `val productCard: ConciergeProductCardBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:234` |
| MUI-0994 | `ConciergeThemeBehavior` | `val multimodalCarousel: ConciergeMultimodalCarouselBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:235` |
| MUI-0995 | `ConciergeThemeBehavior` | `val chat: ConciergeChatBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:236` |
| MUI-0996 | `ConciergeThemeBehavior` | `val promptSuggestions: ConciergePromptSuggestionsBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:237` |
| MUI-0997 | `ConciergeThemeBehavior` | `val privacyNotice: PrivacyNoticeConfig` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:238` |
| MUI-0998 | `ConciergePromptSuggestionsBehavior` | `val itemMaxLines: Int` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:242` |
| MUI-0999 | `ConciergePromptSuggestionsBehavior` | `val showHeader: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:243` |
| MUI-1000 | `ConciergeChatBehavior` | `val messageAlignment: ConciergeTextAlignment` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:250` |
| MUI-1001 | `ConciergeChatBehavior` | `val messageWidth: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:251` |
| MUI-1002 | `ConciergeChatBehavior` | `val userMessageBubbleStyle: UserMessageBubbleStyle` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:252` |
| MUI-1003 | `ConciergeFeedbackBehavior` | `val displayMode: FeedbackDisplayMode` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:362` |
| MUI-1004 | `ConciergeFeedbackBehavior` | `val thumbsPlacement: FeedbackThumbsPlacement` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:363` |
| MUI-1005 | `ConciergeFeedbackBehavior` | `val showCloseButton: Boolean?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:365` |
| MUI-1006 | `ConciergeFeedbackBehavior` | `val showCancelButton: Boolean?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:367` |
| MUI-1007 | `ConciergeFeedbackBehavior` | `val alwaysDisplay: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:369` |
| MUI-1008 | `ConciergeLinkIconStyle` | `val size: Float?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:396` |
| MUI-1009 | `ConciergeLinkIconStyle` | `val spacing: Float?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:397` |
| MUI-1010 | `ConciergeLinkIconStyle` | `val color: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:398` |
| MUI-1011 | `ConciergeCitationsBehavior` | `val showLinkIcon: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:402` |
| MUI-1012 | `ConciergeCitationsBehavior` | `val phoneIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:403` |
| MUI-1013 | `ConciergeCitationsBehavior` | `val storeIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:404` |
| MUI-1014 | `ConciergeCitationsBehavior` | `val defaultLinkIcon: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:405` |
| MUI-1015 | `ConciergeCitationsBehavior` | `val linkIconStyle: ConciergeLinkIconStyle?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:406` |
| MUI-1016 | `ConciergeProductCardBehavior` | `val cardStyle: ProductCardStyle` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:428` |
| MUI-1017 | `ConciergeProductCardBehavior` | `val cardsAlignment: CardsAlignment` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:429` |
| MUI-1018 | `ConciergeMultimodalCarouselBehavior` | `val carouselStyle: CarouselStyle` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:433` |
| MUI-1019 | `ConciergeWelcomeCardBehavior` | `val closeButtonAlignment: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:437` |
| MUI-1020 | `ConciergeWelcomeCardBehavior` | `val promptFullWidth: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:438` |
| MUI-1021 | `ConciergeWelcomeCardBehavior` | `val promptMaxLines: Int` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:439` |
| MUI-1022 | `ConciergeWelcomeCardBehavior` | `val contentAlignment: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:440` |
| MUI-1023 | `ConciergeThemeAssets` | `val icons: ConciergeIconAssets` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:447` |
| MUI-1024 | `ConciergeThemeAssets` | `val images: ConciergeImageAssets` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:448` |
| MUI-1025 | `ConciergeThemeAssets` | `val fonts: ConciergeThemeFonts` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:449` |
| MUI-1026 | `ConciergeIconAssets` | `val company: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:453` |
| MUI-1027 | `ConciergeIconAssets` | `val send: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:454` |
| MUI-1028 | `ConciergeIconAssets` | `val microphone: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:455` |
| MUI-1029 | `ConciergeIconAssets` | `val close: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:456` |
| MUI-1030 | `ConciergeIconAssets` | `val thumbsUp: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:457` |
| MUI-1031 | `ConciergeIconAssets` | `val thumbsDown: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:458` |
| MUI-1032 | `ConciergeIconAssets` | `val chevronDown: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:459` |
| MUI-1033 | `ConciergeIconAssets` | `val chevronRight: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:460` |
| MUI-1034 | `ConciergeImageAssets` | `val welcomeBanner: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:464` |
| MUI-1035 | `ConciergeImageAssets` | `val errorPlaceholder: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:465` |
| MUI-1036 | `ConciergeImageAssets` | `val avatarBot: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:466` |
| MUI-1037 | `ConciergeImageAssets` | `val avatarUser: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:467` |
| MUI-1038 | `ConciergeThemeFonts` | `val regular: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:471` |
| MUI-1039 | `ConciergeThemeFonts` | `val medium: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:472` |
| MUI-1040 | `ConciergeThemeFonts` | `val bold: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:473` |
| MUI-1041 | `ConciergeThemeFonts` | `val light: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:474` |
| MUI-1042 | `ConciergeThemeContent` | `val text: ConciergeTextContent` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:481` |
| MUI-1043 | `ConciergeThemeContent` | `val placeholders: ConciergePlaceholderContent` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:482` |
| MUI-1044 | `ConciergeThemeContent` | `val accessibility: ConciergeAccessibilityContent` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:483` |
| MUI-1045 | `ConciergeTextContent` | `val welcomeTitle: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:487` |
| MUI-1046 | `ConciergeTextContent` | `val welcomeSubtitle: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:488` |
| MUI-1047 | `ConciergeTextContent` | `val disclaimerText: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:489` |
| MUI-1048 | `ConciergeTextContent` | `val errorTitle: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:490` |
| MUI-1049 | `ConciergeTextContent` | `val errorRetry: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:491` |
| MUI-1050 | `ConciergeTextContent` | `val feedbackTitle: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:492` |
| MUI-1051 | `ConciergeTextContent` | `val feedbackSubmit: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:493` |
| MUI-1052 | `ConciergeTextContent` | `val feedbackCancel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:494` |
| MUI-1053 | `ConciergeTextContent` | `val thinkingLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:495` |
| MUI-1054 | `ConciergeTextContent` | `val listeningLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:496` |
| MUI-1055 | `ConciergePlaceholderContent` | `val inputPlaceholder: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:500` |
| MUI-1056 | `ConciergePlaceholderContent` | `val listeningPlaceholder: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:501` |
| MUI-1057 | `ConciergePlaceholderContent` | `val emptyStateMessage: String?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:502` |
| MUI-1058 | `ConciergeAccessibilityContent` | `val sendButtonLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:506` |
| MUI-1059 | `ConciergeAccessibilityContent` | `val micButtonLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:507` |
| MUI-1060 | `ConciergeAccessibilityContent` | `val closeButtonLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:508` |
| MUI-1061 | `ConciergeAccessibilityContent` | `val thumbsUpLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:509` |
| MUI-1062 | `ConciergeAccessibilityContent` | `val thumbsDownLabel: String` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:510` |
| MUI-1063 | `ConciergeThemeLayout` | `val spacing: ConciergeSpacingLayout` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:517` |
| MUI-1064 | `ConciergeThemeLayout` | `val sizing: ConciergeSizingLayout` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:518` |
| MUI-1065 | `ConciergeThemeLayout` | `val positioning: ConciergePositioningLayout` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:519` |
| MUI-1066 | `ConciergeSpacingLayout` | `val xs: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:523` |
| MUI-1067 | `ConciergeSpacingLayout` | `val sm: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:524` |
| MUI-1068 | `ConciergeSpacingLayout` | `val md: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:525` |
| MUI-1069 | `ConciergeSpacingLayout` | `val lg: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:526` |
| MUI-1070 | `ConciergeSpacingLayout` | `val xl: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:527` |
| MUI-1071 | `ConciergeSpacingLayout` | `val xxl: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:528` |
| MUI-1072 | `ConciergeSizingLayout` | `val iconSm: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:532` |
| MUI-1073 | `ConciergeSizingLayout` | `val iconMd: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:533` |
| MUI-1074 | `ConciergeSizingLayout` | `val iconLg: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:534` |
| MUI-1075 | `ConciergeSizingLayout` | `val avatarSm: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:535` |
| MUI-1076 | `ConciergeSizingLayout` | `val avatarMd: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:536` |
| MUI-1077 | `ConciergeSizingLayout` | `val avatarLg: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:537` |
| MUI-1078 | `ConciergeSizingLayout` | `val buttonHeightSm: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:538` |
| MUI-1079 | `ConciergeSizingLayout` | `val buttonHeightMd: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:539` |
| MUI-1080 | `ConciergeSizingLayout` | `val buttonHeightLg: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:540` |
| MUI-1081 | `ConciergePositioningLayout` | `val headerHeight: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:544` |
| MUI-1082 | `ConciergePositioningLayout` | `val footerHeight: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:545` |
| MUI-1083 | `ConciergePositioningLayout` | `val maxContentWidth: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:546` |
| MUI-1084 | `ConciergePositioningLayout` | `val minContentWidth: Double` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:547` |
| MUI-1085 | `ConciergeThemeLoader` | `fun load(context: Context, filename: String): ConciergeThemeData?` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:52` |
| MUI-1086 | `ConciergeThemeLoader` | `fun default(): ConciergeThemeData` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:86` |
| MUI-1087 | `ConciergeThemeLoader` | `fun loadTokens(context: Context, filename: String): ConciergeThemeTokens?` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:102` |
| MUI-1094 | `ConciergeThemeData` | `ConciergeThemeData(val config: ConciergeThemeConfig, val tokens: ConciergeThemeTokens?)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:22` |
| MUI-1095 | `ConciergeThemeData` | `val config: ConciergeThemeConfig` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:23` |
| MUI-1096 | `ConciergeThemeData` | `val tokens: ConciergeThemeTokens?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:24` |
| MUI-1097 | `ConciergeThemeKt` | `fun ConciergeTheme(darkTheme: Boolean = isSystemInDarkTheme(), theme: ConciergeThemeData? = null, content: @Composable () -> Unit)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:86` |
| MUI-1098 | `ConciergeThemeKt` | `fun ConciergeTheme(darkTheme: Boolean = isSystemInDarkTheme(), themeFileName: String?, content: @Composable () -> Unit)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:144` |
| MUI-1104 | `ConciergeTheme` | `val colors: ConciergeColors` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:171` |
| MUI-1105 | `ConciergeTheme` | `val useDefaultPalette: Boolean` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:177` |
| MUI-1106 | `ConciergeTheme` | `val config: ConciergeThemeConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:183` |
| MUI-1107 | `ConciergeTheme` | `val tokens: ConciergeThemeTokens?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:189` |
| MUI-1108 | `ConciergeTheme` | `val behavior: ConciergeThemeBehavior?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:195` |
| MUI-1109 | `ConciergeTheme` | `val header: HeaderConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:201` |
| MUI-1110 | `ConciergeTheme` | `val text: ConciergeTextStrings?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:207` |
| MUI-1111 | `ConciergeTheme` | `val disclaimer: DisclaimerConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:213` |
| MUI-1112 | `ConciergeTheme` | `val typography: ConciergeTypographyConfig?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:219` |
| MUI-1113 | `ConciergeTheme` | `val colorScheme: ColorScheme` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:226` |
| MUI-1114 | `ConciergeColors` | `ConciergeColors(val primary: Color, val onPrimary: Color, val secondary: Color, val surface: Color, val onSurface: Color, val onSurfaceVariant: Color, val background: Color, val container: Color, val outline: Color, val error: Color, val onError: Color, val userMessageBackground: Color? = null, val userMessageText: Color? = null, val conciergeMessageBackground: Color? = null, val conciergeMessageText: Color? = null, val messageConciergeLink: Color? = null, val buttonPrimaryBackground: Color? = null, val buttonPrimaryText: Color? = null, val buttonPrimaryHover: Color? = null, val buttonSecondaryBorder: Color? = null, val buttonSecondaryText: Color? = null, val buttonSecondaryHover: Color? = null, val buttonSecondaryHoverText: Color? = null, val buttonSubmitFill: Color? = null, val buttonSubmitText: Color? = null, val buttonDisabled: Color? = null, val inputBackground: Color? = null, val inputText: Color? = null, val inputOutline: Color? = null, val inputOutlineGradient: ConciergeGradient? = null, val inputOutlineFocus: Color? = null, val micButtonColor: Color? = null, val sendIconColor: Color? = null, val sendArrowIconColor: Color? = null, val sendArrowBackgroundColor: Color? = null, val sendArrowBackgroundGradient: ConciergeGradient? = null, val micIconColor: Color? = null, val micIconGradient: ConciergeGradient? = null, val micRecordingIconColor: Color? = null, val micWaveformGradient: ConciergeGradient? = null, val feedbackIconButtonBackground: Color? = null, val feedbackIconButtonHoverBackground: Color? = null, val feedbackDialogCheckboxCheckedColor: Color? = null, val feedbackDialogCancelButtonColor: Color? = null, val feedbackDialogSubmitButtonColor: Color? = null, val feedbackDialogSubmitButtonTextColor: Color? = null, val feedbackSheetBackground: Color? = null, val feedbackTitleText: Color? = null, val feedbackQuestionText: Color? = null, val feedbackOptionsText: Color? = null, val feedbackCheckboxBorder: Color? = null, val feedbackDragHandle: Color? = null, val feedbackSubmitButtonFill: Color? = null, val feedbackSubmitButtonText: Color? = null, val feedbackCancelButtonFill: Color? = null, val feedbackCancelButtonText: Color? = null, val feedbackCancelButtonBorder: Color? = null, val welcomePromptBackground: Color? = null, val welcomePromptText: Color? = null, val suggestionBackground: Color? = null, val suggestionText: Color? = null, val citationBackground: Color? = null, val citationText: Color? = null, val disclaimerColor: Color? = null, val ctaButtonBackground: Color? = null, val ctaButtonText: Color? = null, val ctaButtonIcon: Color? = null, val productCardCtaButtonBackground: Color? = null, val productCardCtaButtonText: Color? = null, val productCardSecondaryCtaButtonBackground: Color? = null, val productCardSecondaryCtaButtonText: Color? = null, val productCardSecondaryCtaButtonBorder: Color? = null, val thinkingDotColor: Color? = null)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:23` |
| MUI-1115 | `ConciergeColors` | `val primary: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:25` |
| MUI-1116 | `ConciergeColors` | `val onPrimary: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:26` |
| MUI-1117 | `ConciergeColors` | `val secondary: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:27` |
| MUI-1118 | `ConciergeColors` | `val surface: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:29` |
| MUI-1119 | `ConciergeColors` | `val onSurface: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:30` |
| MUI-1120 | `ConciergeColors` | `val onSurfaceVariant: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:31` |
| MUI-1121 | `ConciergeColors` | `val background: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:33` |
| MUI-1122 | `ConciergeColors` | `val container: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:35` |
| MUI-1123 | `ConciergeColors` | `val outline: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:37` |
| MUI-1124 | `ConciergeColors` | `val error: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:39` |
| MUI-1125 | `ConciergeColors` | `val onError: Color` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:40` |
| MUI-1126 | `ConciergeColors` | `val userMessageBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:43` |
| MUI-1127 | `ConciergeColors` | `val userMessageText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:44` |
| MUI-1128 | `ConciergeColors` | `val conciergeMessageBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:45` |
| MUI-1129 | `ConciergeColors` | `val conciergeMessageText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:46` |
| MUI-1130 | `ConciergeColors` | `val messageConciergeLink: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:47` |
| MUI-1131 | `ConciergeColors` | `val buttonPrimaryBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:50` |
| MUI-1132 | `ConciergeColors` | `val buttonPrimaryText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:51` |
| MUI-1133 | `ConciergeColors` | `val buttonPrimaryHover: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:52` |
| MUI-1134 | `ConciergeColors` | `val buttonSecondaryBorder: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:53` |
| MUI-1135 | `ConciergeColors` | `val buttonSecondaryText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:54` |
| MUI-1136 | `ConciergeColors` | `val buttonSecondaryHover: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:55` |
| MUI-1137 | `ConciergeColors` | `val buttonSecondaryHoverText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:56` |
| MUI-1138 | `ConciergeColors` | `val buttonSubmitFill: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:57` |
| MUI-1139 | `ConciergeColors` | `val buttonSubmitText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:58` |
| MUI-1140 | `ConciergeColors` | `val buttonDisabled: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:59` |
| MUI-1141 | `ConciergeColors` | `val inputBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:62` |
| MUI-1142 | `ConciergeColors` | `val inputText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:63` |
| MUI-1143 | `ConciergeColors` | `val inputOutline: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:64` |
| MUI-1144 | `ConciergeColors` | `val inputOutlineGradient: ConciergeGradient?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:65` |
| MUI-1145 | `ConciergeColors` | `val inputOutlineFocus: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:66` |
| MUI-1146 | `ConciergeColors` | `val micButtonColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:67` |
| MUI-1147 | `ConciergeColors` | `val sendIconColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:68` |
| MUI-1148 | `ConciergeColors` | `val sendArrowIconColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:69` |
| MUI-1149 | `ConciergeColors` | `val sendArrowBackgroundColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:70` |
| MUI-1150 | `ConciergeColors` | `val sendArrowBackgroundGradient: ConciergeGradient?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:71` |
| MUI-1151 | `ConciergeColors` | `val micIconColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:72` |
| MUI-1152 | `ConciergeColors` | `val micIconGradient: ConciergeGradient?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:73` |
| MUI-1153 | `ConciergeColors` | `val micRecordingIconColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:74` |
| MUI-1154 | `ConciergeColors` | `val micWaveformGradient: ConciergeGradient?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:75` |
| MUI-1155 | `ConciergeColors` | `val feedbackIconButtonBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:78` |
| MUI-1156 | `ConciergeColors` | `val feedbackIconButtonHoverBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:79` |
| MUI-1157 | `ConciergeColors` | `val feedbackDialogCheckboxCheckedColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:82` |
| MUI-1158 | `ConciergeColors` | `val feedbackDialogCancelButtonColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:83` |
| MUI-1159 | `ConciergeColors` | `val feedbackDialogSubmitButtonColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:84` |
| MUI-1160 | `ConciergeColors` | `val feedbackDialogSubmitButtonTextColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:85` |
| MUI-1161 | `ConciergeColors` | `val feedbackSheetBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:88` |
| MUI-1162 | `ConciergeColors` | `val feedbackTitleText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:89` |
| MUI-1163 | `ConciergeColors` | `val feedbackQuestionText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:90` |
| MUI-1164 | `ConciergeColors` | `val feedbackOptionsText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:91` |
| MUI-1165 | `ConciergeColors` | `val feedbackCheckboxBorder: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:92` |
| MUI-1166 | `ConciergeColors` | `val feedbackDragHandle: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:93` |
| MUI-1167 | `ConciergeColors` | `val feedbackSubmitButtonFill: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:94` |
| MUI-1168 | `ConciergeColors` | `val feedbackSubmitButtonText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:95` |
| MUI-1169 | `ConciergeColors` | `val feedbackCancelButtonFill: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:96` |
| MUI-1170 | `ConciergeColors` | `val feedbackCancelButtonText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:97` |
| MUI-1171 | `ConciergeColors` | `val feedbackCancelButtonBorder: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:98` |
| MUI-1172 | `ConciergeColors` | `val welcomePromptBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:101` |
| MUI-1173 | `ConciergeColors` | `val welcomePromptText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:102` |
| MUI-1174 | `ConciergeColors` | `val suggestionBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:105` |
| MUI-1175 | `ConciergeColors` | `val suggestionText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:106` |
| MUI-1176 | `ConciergeColors` | `val citationBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:109` |
| MUI-1177 | `ConciergeColors` | `val citationText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:110` |
| MUI-1178 | `ConciergeColors` | `val disclaimerColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:111` |
| MUI-1179 | `ConciergeColors` | `val ctaButtonBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:114` |
| MUI-1180 | `ConciergeColors` | `val ctaButtonText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:115` |
| MUI-1181 | `ConciergeColors` | `val ctaButtonIcon: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:116` |
| MUI-1182 | `ConciergeColors` | `val productCardCtaButtonBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:119` |
| MUI-1183 | `ConciergeColors` | `val productCardCtaButtonText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:120` |
| MUI-1184 | `ConciergeColors` | `val productCardSecondaryCtaButtonBackground: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:123` |
| MUI-1185 | `ConciergeColors` | `val productCardSecondaryCtaButtonText: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:124` |
| MUI-1186 | `ConciergeColors` | `val productCardSecondaryCtaButtonBorder: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:125` |
| MUI-1187 | `ConciergeColors` | `val thinkingDotColor: Color?` [g/e/t] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:128` |
| MUI-1224 | `ConciergeTextAlignment` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:261` |
| MUI-1225 | `UserMessageBubbleStyle` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:283` |
| MUI-1226 | `FeedbackDisplayMode` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:299` |
| MUI-1227 | `ThinkingDotVerticalAlignment` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:322` |
| MUI-1228 | `ProductCardStyle` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:337` |
| MUI-1229 | `CarouselStyle` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:351` |
| MUI-1230 | `FeedbackThumbsPlacement` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:384` |
| MUI-1231 | `CardsAlignment` | `val value: String` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:416` |
| MUI-1238 | `ConciergeGradient` | `ConciergeGradient(val startColor: Color, val endColor: Color, val angle: Float = 180f)` | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:37` |
| MUI-1239 | `ConciergeGradient` | `val startColor: Color` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:38` |
| MUI-1240 | `ConciergeGradient` | `val endColor: Color` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:39` |
| MUI-1241 | `ConciergeGradient` | `val angle: Float` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:41` |
| MUI-1242 | `ConciergeGradient` | `val isRenderable: Boolean` [get] | NO_EVENT | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:49` |

### Supporting infrastructure — not customer extension API counts

**267 declarations**, separately classified. Shared UI models/styles/contracts remain explicit rather than disappearing; FCM and plugin callbacks retain downstream event references.

#### FCM service integration infrastructure

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0145 | `MessagingService` | `public void onNewToken(final @NonNull String token)` | CORE_PUSH_IDENTIFIER | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:45` |
| MUI-0146 | `MessagingService` | `public void onMessageReceived(final @NonNull RemoteMessage remoteMessage)` | MSG_PUSH_RECEIVE, MSG_PUSH_EDGE, LIVE_RENDER | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:55` |
| MUI-0147 | `MessagingService` | `public static boolean handleRemoteMessage( final @NonNull Context context, final @NonNull RemoteMessage remoteMessage)` | MSG_PUSH_RECEIVE, MSG_PUSH_EDGE, LIVE_RENDER | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:77` |
| MUI-1210 | `MessagingService` | `public static boolean isAJONotification(final @NonNull RemoteMessage remoteMessage)` | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:268` |

#### Messaging Compose infrastructure appendix

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0173 | `ImageOnlyUI` | `ImageOnlyUI(private val template: ImageOnlyTemplate, state: ImageOnlyCardUIState)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/ImageOnlyUI.kt:23` |
| MUI-0174 | `LargeImageUI` | `LargeImageUI(private val template: LargeImageTemplate, state: LargeImageCardUIState)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/LargeImageUI.kt:23` |
| MUI-0175 | `SmallImageUI` | `SmallImageUI(private val template: SmallImageTemplate, state: SmallImageCardUIState)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/SmallImageUI.kt:23` |
| MUI-0176 | `AepUI` | `fun getTemplate(): T` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:33` |
| MUI-0177 | `AepUI` | `fun getState(): S` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:41` |
| MUI-0178 | `AepUI` | `fun updateState(newState: S)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:49` |
| MUI-0179 | `BaseAepUI` | `fun updateState(newState: S)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:35` |
| MUI-0180 | `BaseAepUI` | `fun getTemplate(): T` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:44` |
| MUI-0181 | `BaseAepUI` | `fun getState(): S` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:53` |
| MUI-0182 | `UIEvent.Display` | `UIEvent.Display(override val aepUi: AepUI<T, S>)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:33` |
| MUI-0183 | `UIEvent.Interact` | `UIEvent.Interact(override val aepUi: AepUI<T, S>, val action: UIAction)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:53` |
| MUI-0184 | `UIEvent.Dismiss` | `UIEvent.Dismiss(override val aepUi: AepUI<T, S>)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:65` |
| MUI-0185 | `UIEvent.Interact` | `val aepUi: AepUI<T, S>` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:54` |
| MUI-0186 | `UIEvent.Interact` | `val action: UIAction` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:55` |
| MUI-0187 | `InboxEvent.Display` | `InboxEvent.Display(override val inboxUIState: InboxUIState.Success)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/InboxEvent.kt:32` |
| MUI-0188 | `UIAction.Click` | `UIAction.Click(val id: String, val actionUrl: String?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIAction.kt:23` |
| MUI-0189 | `AepButton` | `AepButton(val id: String, val actionUrl: String, val text: AepText)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:21` |
| MUI-0190 | `AepButton` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:22` |
| MUI-0191 | `AepButton` | `val actionUrl: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:23` |
| MUI-0192 | `AepButton` | `val text: AepText` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:24` |
| MUI-0193 | `AepColor` | `AepColor(val light: Color, val dark: Color?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:22` |
| MUI-0194 | `AepColor` | `val light: Color` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:23` |
| MUI-0195 | `AepColor` | `val dark: Color?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:24` |
| MUI-0196 | `AepIcon` | `AepIcon(val drawableId: Int)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepIcon.kt:19` |
| MUI-0197 | `AepImage` | `AepImage(val url: String? = null, val darkUrl: String? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:20` |
| MUI-0198 | `AepImage` | `val url: String?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:21` |
| MUI-0199 | `AepImage` | `val darkUrl: String?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:22` |
| MUI-0200 | `AepInboxLayout` | `fun from(typeName: String?): AepInboxLayout?` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepInboxLayout.kt:26` |
| MUI-0201 | `AepText` | `AepText(val content: String)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepText.kt:19` |
| MUI-0202 | `AepText` | `val content: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepText.kt:20` |
| MUI-0203 | `AepUITemplate` | `fun getType(): AepUITemplateType` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepUITemplate.kt:24` |
| MUI-0204 | `AepUITemplate` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepUITemplate.kt:18` |
| MUI-0205 | `ImageOnlyTemplate` | `fun getType()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:34` |
| MUI-0206 | `ImageOnlyTemplate` | `ImageOnlyTemplate(override val id: String, val image: AepImage, val actionUrl: String? = null, val dismissBtn: AepIcon? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:22` |
| MUI-0207 | `ImageOnlyTemplate` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:23` |
| MUI-0208 | `ImageOnlyTemplate` | `val image: AepImage` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:24` |
| MUI-0209 | `ImageOnlyTemplate` | `val actionUrl: String?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:25` |
| MUI-0210 | `ImageOnlyTemplate` | `val dismissBtn: AepIcon?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:26` |
| MUI-0211 | `InboxTemplate` | `InboxTemplate(val id: String, val heading: AepText, val layout: AepInboxLayout, val capacity: Int, val emptyMessage: AepText? = null, val emptyImage: AepImage? = null, val isUnreadEnabled: Boolean = false, val unreadBgColor: AepColor? = null, val unreadIcon: AepImage? = null, val unreadIconAlignment: Alignment? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:16` |
| MUI-0212 | `InboxTemplate` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:17` |
| MUI-0213 | `InboxTemplate` | `val heading: AepText` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:18` |
| MUI-0214 | `InboxTemplate` | `val layout: AepInboxLayout` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:19` |
| MUI-0215 | `InboxTemplate` | `val capacity: Int` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:20` |
| MUI-0216 | `InboxTemplate` | `val emptyMessage: AepText?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:21` |
| MUI-0217 | `InboxTemplate` | `val emptyImage: AepImage?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:22` |
| MUI-0218 | `InboxTemplate` | `val isUnreadEnabled: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:23` |
| MUI-0219 | `InboxTemplate` | `val unreadBgColor: AepColor?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:24` |
| MUI-0220 | `InboxTemplate` | `val unreadIcon: AepImage?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:25` |
| MUI-0221 | `InboxTemplate` | `val unreadIconAlignment: Alignment?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:26` |
| MUI-0222 | `LargeImageTemplate` | `fun getType()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:40` |
| MUI-0223 | `LargeImageTemplate` | `LargeImageTemplate(override val id: String, val title: AepText, val body: AepText? = null, val image: AepImage? = null, val actionUrl: String? = null, val buttons: List<AepButton>? = null, val dismissBtn: AepIcon? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:25` |
| MUI-0224 | `LargeImageTemplate` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:26` |
| MUI-0225 | `LargeImageTemplate` | `val title: AepText` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:27` |
| MUI-0226 | `LargeImageTemplate` | `val body: AepText?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:28` |
| MUI-0227 | `LargeImageTemplate` | `val image: AepImage?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:29` |
| MUI-0228 | `LargeImageTemplate` | `val actionUrl: String?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:30` |
| MUI-0229 | `LargeImageTemplate` | `val buttons: List<AepButton>?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:31` |
| MUI-0230 | `LargeImageTemplate` | `val dismissBtn: AepIcon?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:32` |
| MUI-0231 | `SmallImageTemplate` | `fun getType()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:40` |
| MUI-0232 | `SmallImageTemplate` | `SmallImageTemplate(override val id: String, val title: AepText, val body: AepText? = null, val image: AepImage? = null, val actionUrl: String? = null, val buttons: List<AepButton>? = null, val dismissBtn: AepIcon? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:25` |
| MUI-0233 | `SmallImageTemplate` | `val id: String` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:26` |
| MUI-0234 | `SmallImageTemplate` | `val title: AepText` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:27` |
| MUI-0235 | `SmallImageTemplate` | `val body: AepText?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:28` |
| MUI-0236 | `SmallImageTemplate` | `val image: AepImage?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:29` |
| MUI-0237 | `SmallImageTemplate` | `val actionUrl: String?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:30` |
| MUI-0238 | `SmallImageTemplate` | `val buttons: List<AepButton>?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:31` |
| MUI-0239 | `SmallImageTemplate` | `val dismissBtn: AepIcon?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:32` |
| MUI-0240 | `AepCardUIState` | `AepCardUIState(open val dismissed: Boolean, open val displayed: Boolean, open val read: Boolean?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:23` |
| MUI-0241 | `AepCardUIState` | `val dismissed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:24` |
| MUI-0242 | `AepCardUIState` | `val displayed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:25` |
| MUI-0243 | `AepCardUIState` | `val read: Boolean?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:26` |
| MUI-0244 | `ImageOnlyCardUIState` | `ImageOnlyCardUIState(override val dismissed: Boolean = false, override val displayed: Boolean = false, override val read: Boolean? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:20` |
| MUI-0245 | `ImageOnlyCardUIState` | `val dismissed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:21` |
| MUI-0246 | `ImageOnlyCardUIState` | `val displayed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:22` |
| MUI-0247 | `ImageOnlyCardUIState` | `val read: Boolean?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:23` |
| MUI-0248 | `InboxUIState.Success` | `InboxUIState.Success(val template: InboxTemplate, val items: List<AepUI<*, *>>, internal val displayed: Boolean = false)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:33` |
| MUI-0249 | `InboxUIState.Error` | `InboxUIState.Error(val error: Throwable)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:44` |
| MUI-0250 | `InboxUIState.Success` | `val template: InboxTemplate` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:34` |
| MUI-0251 | `InboxUIState.Success` | `val items: List<AepUI<*, *>>` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:35` |
| MUI-0252 | `InboxUIState.Error` | `val error: Throwable` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:45` |
| MUI-0253 | `LargeImageCardUIState` | `LargeImageCardUIState(override val dismissed: Boolean = false, override val displayed: Boolean = false, override val read: Boolean? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:20` |
| MUI-0254 | `LargeImageCardUIState` | `val dismissed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:21` |
| MUI-0255 | `LargeImageCardUIState` | `val displayed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:22` |
| MUI-0256 | `LargeImageCardUIState` | `val read: Boolean?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:23` |
| MUI-0257 | `SmallImageCardUIState` | `SmallImageCardUIState(override val dismissed: Boolean = false, override val displayed: Boolean = false, override val read: Boolean? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:20` |
| MUI-0258 | `SmallImageCardUIState` | `val dismissed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:21` |
| MUI-0259 | `SmallImageCardUIState` | `val displayed: Boolean` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:22` |
| MUI-0260 | `SmallImageCardUIState` | `val read: Boolean?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:23` |
| MUI-0261 | `AepButtonStyle` | `AepButtonStyle(var modifier: Modifier? = null, var enabled: Boolean? = null, var elevation: ButtonElevation? = null, var shape: Shape? = null, var border: BorderStroke? = null, var colors: ButtonColors? = null, var contentPadding: PaddingValues? = null, var interactionSource: MutableInteractionSource? = null, var textStyle: AepTextStyle? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:34` |
| MUI-0262 | `AepButtonStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:35` |
| MUI-0263 | `AepButtonStyle` | `var enabled: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:36` |
| MUI-0264 | `AepButtonStyle` | `var elevation: ButtonElevation?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:37` |
| MUI-0265 | `AepButtonStyle` | `var shape: Shape?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:38` |
| MUI-0266 | `AepButtonStyle` | `var border: BorderStroke?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:39` |
| MUI-0267 | `AepButtonStyle` | `var colors: ButtonColors?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:40` |
| MUI-0268 | `AepButtonStyle` | `var contentPadding: PaddingValues?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:41` |
| MUI-0269 | `AepButtonStyle` | `var interactionSource: MutableInteractionSource?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:42` |
| MUI-0270 | `AepButtonStyle` | `var textStyle: AepTextStyle?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:43` |
| MUI-0271 | `AepCardStyle` | `AepCardStyle(var modifier: Modifier? = null, var enabled: Boolean? = null, var shape: Shape? = null, var colors: CardColors? = null, var elevation: CardElevation? = null, var border: BorderStroke? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:30` |
| MUI-0272 | `AepCardStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:31` |
| MUI-0273 | `AepCardStyle` | `var enabled: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:32` |
| MUI-0274 | `AepCardStyle` | `var shape: Shape?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:33` |
| MUI-0275 | `AepCardStyle` | `var colors: CardColors?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:34` |
| MUI-0276 | `AepCardStyle` | `var elevation: CardElevation?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:35` |
| MUI-0277 | `AepCardStyle` | `var border: BorderStroke?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:36` |
| MUI-0278 | `AepColumnStyle` | `AepColumnStyle(var modifier: Modifier? = null, var verticalArrangement: Arrangement.Vertical? = null, var horizontalAlignment: Alignment.Horizontal? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:25` |
| MUI-0279 | `AepColumnStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:26` |
| MUI-0280 | `AepColumnStyle` | `var verticalArrangement: Arrangement.Vertical?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:27` |
| MUI-0281 | `AepColumnStyle` | `var horizontalAlignment: Alignment.Horizontal?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:28` |
| MUI-0282 | `AepIconStyle` | `AepIconStyle(var modifier: Modifier? = null, var contentDescription: String? = null, var tint: Color? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:25` |
| MUI-0283 | `AepIconStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:26` |
| MUI-0284 | `AepIconStyle` | `var contentDescription: String?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:27` |
| MUI-0285 | `AepIconStyle` | `var tint: Color?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:28` |
| MUI-0286 | `AepImageStyle` | `AepImageStyle(var modifier: Modifier? = null, var contentDescription: String? = null, var alignment: Alignment? = null, var contentScale: ContentScale? = null, var alpha: Float? = null, var colorFilter: ColorFilter? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:29` |
| MUI-0287 | `AepImageStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:30` |
| MUI-0288 | `AepImageStyle` | `var contentDescription: String?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:31` |
| MUI-0289 | `AepImageStyle` | `var alignment: Alignment?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:32` |
| MUI-0290 | `AepImageStyle` | `var contentScale: ContentScale?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:33` |
| MUI-0291 | `AepImageStyle` | `var alpha: Float?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:34` |
| MUI-0292 | `AepImageStyle` | `var colorFilter: ColorFilter?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:35` |
| MUI-0293 | `AepLazyColumnStyle` | `AepLazyColumnStyle(var modifier: Modifier? = null, var contentPadding: PaddingValues? = null, var reverseLayout: Boolean? = null, var verticalArrangement: Arrangement.Vertical? = null, var horizontalAlignment: Alignment.Horizontal? = null, var flingBehavior: FlingBehavior? = null, var userScrollEnabled: Boolean? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:31` |
| MUI-0294 | `AepLazyColumnStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:32` |
| MUI-0295 | `AepLazyColumnStyle` | `var contentPadding: PaddingValues?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:33` |
| MUI-0296 | `AepLazyColumnStyle` | `var reverseLayout: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:34` |
| MUI-0297 | `AepLazyColumnStyle` | `var verticalArrangement: Arrangement.Vertical?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:35` |
| MUI-0298 | `AepLazyColumnStyle` | `var horizontalAlignment: Alignment.Horizontal?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:36` |
| MUI-0299 | `AepLazyColumnStyle` | `var flingBehavior: FlingBehavior?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:37` |
| MUI-0300 | `AepLazyColumnStyle` | `var userScrollEnabled: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:38` |
| MUI-0301 | `AepLazyRowStyle` | `AepLazyRowStyle(var modifier: Modifier? = null, var contentPadding: PaddingValues? = null, var reverseLayout: Boolean? = null, var horizontalArrangement: Arrangement.Horizontal? = null, var verticalAlignment: Alignment.Vertical? = null, var flingBehavior: FlingBehavior? = null, var userScrollEnabled: Boolean? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:20` |
| MUI-0302 | `AepLazyRowStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:21` |
| MUI-0303 | `AepLazyRowStyle` | `var contentPadding: PaddingValues?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:22` |
| MUI-0304 | `AepLazyRowStyle` | `var reverseLayout: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:23` |
| MUI-0305 | `AepLazyRowStyle` | `var horizontalArrangement: Arrangement.Horizontal?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:24` |
| MUI-0306 | `AepLazyRowStyle` | `var verticalAlignment: Alignment.Vertical?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:25` |
| MUI-0307 | `AepLazyRowStyle` | `var flingBehavior: FlingBehavior?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:26` |
| MUI-0308 | `AepLazyRowStyle` | `var userScrollEnabled: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:27` |
| MUI-0309 | `AepRowStyle` | `AepRowStyle(var modifier: Modifier? = null, var horizontalArrangement: Arrangement.Horizontal? = null, var verticalAlignment: Alignment.Vertical? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:25` |
| MUI-0310 | `AepRowStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:26` |
| MUI-0311 | `AepRowStyle` | `var horizontalArrangement: Arrangement.Horizontal?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:27` |
| MUI-0312 | `AepRowStyle` | `var verticalAlignment: Alignment.Vertical?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:28` |
| MUI-0313 | `AepTextStyle` | `AepTextStyle(var modifier: Modifier? = null, var textStyle: TextStyle? = null, var overflow: TextOverflow? = null, var softWrap: Boolean? = null, var maxLines: Int? = null, var minLines: Int? = null)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:28` |
| MUI-0314 | `AepTextStyle` | `var modifier: Modifier?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:29` |
| MUI-0315 | `AepTextStyle` | `var textStyle: TextStyle?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:30` |
| MUI-0316 | `AepTextStyle` | `var overflow: TextOverflow?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:31` |
| MUI-0317 | `AepTextStyle` | `var softWrap: Boolean?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:32` |
| MUI-0318 | `AepTextStyle` | `var maxLines: Int?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:33` |
| MUI-0319 | `AepTextStyle` | `var minLines: Int?` [g/e/t///s/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:34` |
| MUI-0320 | `AepUIStyle` | `AepUIStyle(val smallImageUIStyle: SmallImageUIStyle = SmallImageUIStyle.Builder().build(), val largeImageUIStyle: LargeImageUIStyle = LargeImageUIStyle.Builder().build(), val imageOnlyUIStyle: ImageOnlyUIStyle = ImageOnlyUIStyle.Builder().build())` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:25` |
| MUI-0321 | `AepUIStyle` | `val smallImageUIStyle: SmallImageUIStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:26` |
| MUI-0322 | `AepUIStyle` | `val largeImageUIStyle: LargeImageUIStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:27` |
| MUI-0323 | `AepUIStyle` | `val imageOnlyUIStyle: ImageOnlyUIStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:28` |
| MUI-0324 | `ImageOnlyUIStyle.Builder` | `fun cardStyle(style: AepCardStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:62` |
| MUI-0325 | `ImageOnlyUIStyle.Builder` | `fun imageStyle(style: AepImageStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:63` |
| MUI-0326 | `ImageOnlyUIStyle.Builder` | `fun dismissButtonStyle(style: AepIconStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:64` |
| MUI-0327 | `ImageOnlyUIStyle.Builder` | `fun dismissButtonAlignment(alignment: Alignment)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:65` |
| MUI-0328 | `ImageOnlyUIStyle.Builder` | `fun build()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:68` |
| MUI-0329 | `ImageOnlyUIStyle.Builder` | `ImageOnlyUIStyle.Builder()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:56` |
| MUI-0330 | `ImageOnlyUIStyle` | `val cardStyle: AepCardStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:33` |
| MUI-0331 | `ImageOnlyUIStyle` | `val imageStyle: AepImageStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:34` |
| MUI-0332 | `ImageOnlyUIStyle` | `val dismissButtonStyle: AepIconStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:35` |
| MUI-0333 | `ImageOnlyUIStyle` | `val dismissButtonAlignment: Alignment` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:36` |
| MUI-0334 | `InboxUIStyle.Builder` | `fun headingStyle(headingStyle: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:90` |
| MUI-0335 | `InboxUIStyle.Builder` | `fun lazyColumnStyle(listStyle: AepLazyColumnStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:92` |
| MUI-0336 | `InboxUIStyle.Builder` | `fun lazyRowStyle(listStyle: AepLazyRowStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:94` |
| MUI-0337 | `InboxUIStyle.Builder` | `fun emptyMessageStyle(emptyMessageStyle: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:96` |
| MUI-0338 | `InboxUIStyle.Builder` | `fun emptyImageStyle(emptyImageStyle: AepImageStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:99` |
| MUI-0339 | `InboxUIStyle.Builder` | `fun unreadIconStyle(unreadIconStyle: AepImageStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:102` |
| MUI-0340 | `InboxUIStyle.Builder` | `fun unreadIconAlignment(unreadIconAlignment: Alignment)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:105` |
| MUI-0341 | `InboxUIStyle.Builder` | `fun unreadBgColor(unreadBgColor: AepColor)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:108` |
| MUI-0342 | `InboxUIStyle.Builder` | `fun loadingView(loadingView: @Composable () -> Unit)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:110` |
| MUI-0343 | `InboxUIStyle.Builder` | `fun errorView(errorView: @Composable () -> Unit)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:113` |
| MUI-0344 | `InboxUIStyle.Builder` | `fun build()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:116` |
| MUI-0345 | `InboxUIStyle.Builder` | `InboxUIStyle.Builder()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:78` |
| MUI-0346 | `InboxUIStyle` | `val headingStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:41` |
| MUI-0347 | `InboxUIStyle` | `val lazyColumnStyle: AepLazyColumnStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:42` |
| MUI-0348 | `InboxUIStyle` | `val lazyRowStyle: AepLazyRowStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:43` |
| MUI-0349 | `InboxUIStyle` | `val unreadIconStyle: AepImageStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:44` |
| MUI-0350 | `InboxUIStyle` | `val unreadIconAlignment: Alignment?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:45` |
| MUI-0351 | `InboxUIStyle` | `val unreadBgColor: AepColor?` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:46` |
| MUI-0352 | `InboxUIStyle` | `val emptyMessageStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:47` |
| MUI-0353 | `InboxUIStyle` | `val emptyImageStyle: AepImageStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:48` |
| MUI-0354 | `InboxUIStyle` | `val loadingView: @Composable () -> Unit` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:49` |
| MUI-0355 | `InboxUIStyle` | `val errorView: @Composable () -> Unit` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:50` |
| MUI-0356 | `LargeImageUIStyle.Builder` | `fun cardStyle(style: AepCardStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:125` |
| MUI-0357 | `LargeImageUIStyle.Builder` | `fun rootColumnStyle(style: AepColumnStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:126` |
| MUI-0358 | `LargeImageUIStyle.Builder` | `fun imageStyle(style: AepImageStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:127` |
| MUI-0359 | `LargeImageUIStyle.Builder` | `fun textColumnStyle(style: AepColumnStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:128` |
| MUI-0360 | `LargeImageUIStyle.Builder` | `fun titleAepTextStyle(style: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:129` |
| MUI-0361 | `LargeImageUIStyle.Builder` | `fun bodyAepTextStyle(style: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:130` |
| MUI-0362 | `LargeImageUIStyle.Builder` | `fun buttonRowStyle(style: AepRowStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:131` |
| MUI-0363 | `LargeImageUIStyle.Builder` | `fun buttonStyle(style: Array<AepButtonStyle?>)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:132` |
| MUI-0364 | `LargeImageUIStyle.Builder` | `fun dismissButtonStyle(style: AepIconStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:133` |
| MUI-0365 | `LargeImageUIStyle.Builder` | `fun dismissButtonAlignment(alignment: Alignment)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:134` |
| MUI-0366 | `LargeImageUIStyle.Builder` | `fun build()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:137` |
| MUI-0367 | `LargeImageUIStyle.Builder` | `LargeImageUIStyle.Builder()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:113` |
| MUI-0368 | `LargeImageUIStyle` | `val cardStyle: AepCardStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:43` |
| MUI-0369 | `LargeImageUIStyle` | `val rootColumnStyle: AepColumnStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:44` |
| MUI-0370 | `LargeImageUIStyle` | `val imageStyle: AepImageStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:45` |
| MUI-0371 | `LargeImageUIStyle` | `val textColumnStyle: AepColumnStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:46` |
| MUI-0372 | `LargeImageUIStyle` | `val titleTextStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:47` |
| MUI-0373 | `LargeImageUIStyle` | `val bodyTextStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:48` |
| MUI-0374 | `LargeImageUIStyle` | `val buttonRowStyle: AepRowStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:49` |
| MUI-0375 | `LargeImageUIStyle` | `val buttonStyle: Array<AepButtonStyle>` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:50` |
| MUI-0376 | `LargeImageUIStyle` | `val dismissButtonStyle: AepIconStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:51` |
| MUI-0377 | `LargeImageUIStyle` | `val dismissButtonAlignment: Alignment` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:52` |
| MUI-0378 | `SmallImageUIStyle.Builder` | `fun cardStyle(style: AepCardStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:125` |
| MUI-0379 | `SmallImageUIStyle.Builder` | `fun rootRowStyle(style: AepRowStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:126` |
| MUI-0380 | `SmallImageUIStyle.Builder` | `fun imageStyle(style: AepImageStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:127` |
| MUI-0381 | `SmallImageUIStyle.Builder` | `fun textColumnStyle(style: AepColumnStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:128` |
| MUI-0382 | `SmallImageUIStyle.Builder` | `fun titleAepTextStyle(style: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:129` |
| MUI-0383 | `SmallImageUIStyle.Builder` | `fun bodyAepTextStyle(style: AepTextStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:130` |
| MUI-0384 | `SmallImageUIStyle.Builder` | `fun buttonRowStyle(style: AepRowStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:131` |
| MUI-0385 | `SmallImageUIStyle.Builder` | `fun buttonStyle(style: Array<AepButtonStyle?>)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:132` |
| MUI-0386 | `SmallImageUIStyle.Builder` | `fun dismissButtonStyle(style: AepIconStyle)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:133` |
| MUI-0387 | `SmallImageUIStyle.Builder` | `fun dismissButtonAlignment(alignment: Alignment)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:134` |
| MUI-0388 | `SmallImageUIStyle.Builder` | `fun build()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:137` |
| MUI-0389 | `SmallImageUIStyle.Builder` | `SmallImageUIStyle.Builder()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:113` |
| MUI-0390 | `SmallImageUIStyle` | `val cardStyle: AepCardStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:42` |
| MUI-0391 | `SmallImageUIStyle` | `val rootRowStyle: AepRowStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:43` |
| MUI-0392 | `SmallImageUIStyle` | `val imageStyle: AepImageStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:44` |
| MUI-0393 | `SmallImageUIStyle` | `val textColumnStyle: AepColumnStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:45` |
| MUI-0394 | `SmallImageUIStyle` | `val titleTextStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:46` |
| MUI-0395 | `SmallImageUIStyle` | `val bodyTextStyle: AepTextStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:47` |
| MUI-0396 | `SmallImageUIStyle` | `val buttonRowStyle: AepRowStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:48` |
| MUI-0397 | `SmallImageUIStyle` | `val buttonStyle: Array<AepButtonStyle>` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:49` |
| MUI-0398 | `SmallImageUIStyle` | `val dismissButtonStyle: AepIconStyle` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:50` |
| MUI-0399 | `SmallImageUIStyle` | `val dismissButtonAlignment: Alignment` [g/e/t] | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:51` |
| MUI-0400 | `AepInboxContentProvider` | `fun getInboxUI(): Flow<InboxUIState>` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepInboxContentProvider.kt:26` |
| MUI-0401 | `AepInboxContentProvider` | `suspend fun refresh()` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepInboxContentProvider.kt:31` |
| MUI-0402 | `AepUIContentProvider` | `suspend fun getContent(): Flow<Result<List<AepUITemplate>>>` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:27` |
| MUI-0403 | `AepUIContentProvider` | `fun getUIContent(): Flow<Result<List<AepUITemplate>>>` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:33` |
| MUI-0404 | `AepUIContentProvider` | `suspend fun refreshContent()` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:39` |
| MUI-0405 | `AepInboxEventObserver` | `fun onInboxEvent(event: InboxEvent)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/observers/AepInboxEventObserver.kt:34` |
| MUI-0406 | `AepUIEventObserver` | `fun onEvent(event: UIEvent<*, *>)` | CALLBACK_ONLY | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/observers/AepUIEventObserver.kt:30` |
| MUI-0407 | `AepInboxKt` | `fun AepInbox(uiState: InboxUIState, inboxStyle: InboxUIStyle = InboxUIStyle.Builder().build(), itemsStyle: AepUIStyle = AepUIStyle(), observer: AepInboxEventObserver)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/AepInbox.kt:42` |
| MUI-0408 | `ImageOnlyCardKt` | `fun ImageOnlyCard(ui: ImageOnlyUI, style: ImageOnlyUIStyle, observer: AepUIEventObserver?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/ImageOnlyCard.kt:37` |
| MUI-0409 | `LargeImageCardKt` | `fun LargeImageCard(ui: LargeImageUI, style: LargeImageUIStyle, observer: AepUIEventObserver?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/LargeImageCard.kt:33` |
| MUI-0410 | `SmallImageCardKt` | `fun SmallImageCard(ui: SmallImageUI, style: SmallImageUIStyle, observer: AepUIEventObserver?)` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/SmallImageCard.kt:33` |
| MUI-0411 | `AepCircularProgressIndicatorKt` | `fun AepCircularProgressIndicator()` | UI_INFRA | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/AepCircularProgressIndicator.kt:28` |
| MUI-1236 | `AepInboxLayout` | `val typeName: String` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepInboxLayout.kt:19` |
| MUI-1237 | `AepUITemplateType` | `val typeName: String` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepUITemplateType.kt:19` |
| MUI-1244 | `UIEvent.Display` | `val aepUi: AepUI<T, S>` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:33` |
| MUI-1245 | `UIEvent.Dismiss` | `val aepUi: AepUI<T, S>` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:65` |
| MUI-1246 | `InboxEvent.Display` | `val inboxUIState: InboxUIState.Success` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/InboxEvent.kt:32` |
| MUI-1247 | `UIAction.Click` | `val id: String` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIAction.kt:23` |
| MUI-1248 | `UIAction.Click` | `val actionUrl: String?` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIAction.kt:23` |
| MUI-1249 | `AepIcon` | `val drawableId: Int` [get] | NO_EVENT | `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepIcon.kt:19` |

#### Core-invoked Live Update plugin infrastructure

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-0454 | `LiveUpdatePlugin` | `fun handleLiveUpdatePush(context: Context, message: Any)` | LIVE_RENDER, LIVE_RECEIVE, LIVE_DIAGNOSTIC | `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePlugin.kt:60` |

#### Shared UI NotificationBuilder infrastructure appendix

| ID | Class | Signature / property contract | Profile(s) | Declaration |
|---|---|---|---|---|
| MUI-1199 | `NotificationBuilder` | `fun version(): String` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:64` |
| MUI-1200 | `NotificationBuilder` | `fun constructNotificationBuilder(messageData: Map<String, String>, trackerActivityClass: Class<out Activity>?, broadcastReceiverClass: Class<out BroadcastReceiver>?): NotificationCompat.Builder` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:80` |
| MUI-1201 | `NotificationBuilder` | `fun constructNotificationBuilder(intent: Intent, trackerActivityClass: Class<out Activity>?, broadcastReceiverClass: Class<out BroadcastReceiver>?): NotificationCompat.Builder` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:106` |
| MUI-1202 | `NotificationBuilderPlugin` | `fun buildPushTemplateNotification(messageData: Map<String, String>, trackingProvider: IPushTemplateTrackingProvider): Notification?` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilderPlugin.kt:39` |
| MUI-1203 | `NotificationBuilderPlugin` | `NotificationBuilderPlugin()` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilderPlugin.kt:29` |
| MUI-1204 | `NotificationPriority` | `fun fromString(priorityString: String?): NotificationPriority` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:32` |
| MUI-1205 | `NotificationPriority` | `fun fromValue(value: Int?): NotificationPriority` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:40` |
| MUI-1206 | `NotificationVisibility` | `fun fromString(visibilityString: String?): NotificationVisibility` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:31` |
| MUI-1207 | `NotificationVisibility` | `fun fromValue(visibilityValue: Int?): NotificationVisibility` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:40` |
| MUI-1208 | `RemindLaterHandler` | `fun handleRemindIntent(remindLaterIntent: Intent, broadcastReceiverClass: Class<out BroadcastReceiver>?)` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/RemindLaterHandler.kt:41` |
| MUI-1209 | `NotificationConstructionFailedException` | `NotificationConstructionFailedException(message: String)` | UI_INFRA | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationConstructionFailedException.kt:19` |
| MUI-1232 | `NotificationPriority` | `val value: Int` [get] | NO_EVENT | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:16` |
| MUI-1233 | `NotificationPriority` | `val stringValue: String` [get] | NO_EVENT | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:16` |
| MUI-1234 | `NotificationVisibility` | `val value: Int` [get] | NO_EVENT | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:16` |
| MUI-1235 | `NotificationVisibility` | `val stringValue: String` [get] | NO_EVENT | `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:16` |

### Excluded implementation declarations

These 22 declarations are source-public but implementation-only at the reviewed customer boundary. They are not apiInventory entries.

#### ConciergeThemeLoader

SDK loader implementation instance method: private constructor and internal companion instance; no public instance acquisition API. Public companion load/default/loadTokens remain eligible.

| Former candidate ID | Declaration | Evidence |
|---|---|---|
| MUI-1088 | `fun loadTheme(context: Context, source: String, sourceType: ThemeSourceType = ThemeSourceType.ASSET, useCache: Boolean = true): ConciergeThemeConfig?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:123` |
| MUI-1089 | `fun loadThemeTokens(context: Context, source: String, sourceType: ThemeSourceType = ThemeSourceType.ASSET, useCache: Boolean = true): ConciergeThemeTokens?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:156` |
| MUI-1090 | `fun loadThemeWithFallback(context: Context, primarySource: String, fallbackSource: String, sourceType: ThemeSourceType = ThemeSourceType.ASSET): ConciergeThemeConfig?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:191` |
| MUI-1091 | `fun clearCache(source: String? = null)` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:208` |
| MUI-1092 | `fun validateThemeSource(context: Context, source: String, sourceType: ThemeSourceType = ThemeSourceType.ASSET): Boolean` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:227` |
| MUI-1093 | `fun getAvailableThemes(context: Context, directory: String = ""): List<String>` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:256` |

#### ActiveConciergeTheme

SDK theme-state holder behind private LocalActiveConciergeTheme; public theme getters expose its values, not this implementation wrapper.

| Former candidate ID | Declaration | Evidence |
|---|---|---|
| MUI-1099 | `ActiveConciergeTheme(val colors: ConciergeColors, val config: ConciergeThemeConfig? = null, val themeTokens: ConciergeThemeTokens? = null, val useDefaultPalette: Boolean = false)` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:34` |
| MUI-1100 | `val colors: ConciergeColors` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:35` |
| MUI-1101 | `val config: ConciergeThemeConfig?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:36` |
| MUI-1102 | `val themeTokens: ConciergeThemeTokens?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:37` |
| MUI-1103 | `val useDefaultPalette: Boolean` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:38` |

#### SuggestedPrompt

SDK welcome-rendering state graph; ViewModel welcomeConfig is internal. Customer configuration enters through public ConciergeThemeConfig, not this internal-state model.

| Former candidate ID | Declaration | Evidence |
|---|---|---|
| MUI-1188 | `SuggestedPrompt(val text: String, val imageUrl: String? = null, val backgroundColor: String? = null)` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:20` |
| MUI-1190 | `val text: String` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:21` |
| MUI-1191 | `val imageUrl: String?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:22` |
| MUI-1192 | `val backgroundColor: String?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:23` |

#### WelcomeConfig

SDK welcome-rendering state graph; ViewModel welcomeConfig is internal. Customer configuration enters through public ConciergeThemeConfig, not this internal-state model.

| Former candidate ID | Declaration | Evidence |
|---|---|---|
| MUI-1189 | `WelcomeConfig(val showWelcomeCard: Boolean = true, val firstTimeWelcomeMessage: String? = null, val returningUserWelcomeMessage: String? = null, val welcomeHeader: String = ConciergeConstants.WelcomeCard.DEFAULT_HEADING, val subHeader: String = ConciergeConstants.WelcomeCard.DEFAULT_SUBHEADING, val suggestedPrompts: List<SuggestedPrompt> = emptyList())` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:29` |
| MUI-1193 | `val showWelcomeCard: Boolean` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:33` |
| MUI-1194 | `val firstTimeWelcomeMessage: String?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:39` |
| MUI-1195 | `val returningUserWelcomeMessage: String?` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:45` |
| MUI-1196 | `val welcomeHeader: String` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:50` |
| MUI-1197 | `val subHeader: String` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:55` |
| MUI-1198 | `val suggestedPrompts: List<SuggestedPrompt>` | `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:60` |

### Invalid scanner candidates removed

These are audit findings, not callable APIs or overloads. The named observer outer declarations remain eligible.

| Removed ID | Apparent owner / declaration | Why excluded |
|---|---|---|
| MUI-0043 | `Messaging.public void run()` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0044 | `Messaging.public void fail(final AdobeError adobeError)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0045 | `Messaging.public void call(final Event event)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0048 | `Messaging.public void fail(final AdobeError adobeError)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0049 | `Messaging.public void call(final Event event)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0161 | `ContentCardEventObserver.fun onEvent(event: UIEvent<*, *>)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0166 | `InboxEventObserver.fun onInboxEvent(event: InboxEvent)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0167 | `InboxEventObserver.fun onEvent(event: UIEvent<*, *>)` | Anonymous callback/delegate body; not a direct method on the named customer class. |
| MUI-0473 | `Concierge.val EXTENSION: Class<out Extension>` | @JvmField constant/field, not a callable property API. |

### Reusable event / local-behavior profiles

Call relationships and gates are described above. A shared profile reference does not make every downstream event unconditional. Producers inside excluded infrastructure remain valid downstream evidence.

#### NO_EVENT — NO_EVENT

Source-declared model construction, parsing, serialization, version lookup, property access or local state/style operation. A returned Map is not an EventHub dispatch.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:48-96`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:38-135`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:26-81`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:46`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:54`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:61`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/Message.java:72`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:131`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:167`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:180`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:211`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:215`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:219`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:223`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:227`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:231`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:235`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:239`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:243`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:247`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:251`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:258`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:262`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:271`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:275`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:336`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:470`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:476`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:480`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingPushPayload.java:484`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:55`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:59`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:79`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:93`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:110`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:294`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:364`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:369`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:462`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:467`; `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:740`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:48`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:76`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:85`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:94`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:104`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:172`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:207`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Proposition.java:226`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:57`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:75`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:84`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:93`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:170`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:186`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:214`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:230`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:244`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:258`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:271`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:292`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:313`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:38`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:73`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:77`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:88`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:92`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:102`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:111`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Surface.java:117`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:168`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:177`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:186`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:195`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCard.java:204`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:62`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:80`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:95`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:110`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:126`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:138`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:164`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:173`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:182`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:191`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItem.java:200`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:39`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:50`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:59`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/Feed.java:68`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:67`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:71`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:75`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:79`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:83`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardSchemaData.java:88`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:74`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:78`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:82`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:86`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:90`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/FeedItemSchemaData.java:99`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:99`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:103`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:107`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:111`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:115`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:119`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:123`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InAppSchemaData.java:127`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/JsonContentSchemaData.java:70`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/HtmlContentSchemaData.java:47`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/HtmlContentSchemaData.java:51`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxContentSchemaData.java:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxContentSchemaData.java:48`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaData.java:16`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:49`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:56`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:66`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentType.java:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentType.java:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaType.java:39`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/SchemaType.java:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardUIProvider.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:38`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:41`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:39`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingInboxProvider.kt:43`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InboxEventObserver.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardAepUIExtensions.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/UiTemplateConstructionFailedException.kt:19`; `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:42`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:208`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:218`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:224`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:235`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:241`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:342`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:84`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:168`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:222`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:231`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:41`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:42`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:43`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:44`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:45`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:46`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:47`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:48`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:49`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:50`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:51`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:55`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:60`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePayload.kt:66`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePlugin.kt:56`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:27`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:34`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:51`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:80`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:23`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatView.kt:74`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:24`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:40`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:62`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:72`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:103`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:136`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:147`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:156`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:165`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:199`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:204`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:210`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:218`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:233`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:255`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:262`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:289`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:294`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:299`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:305`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:311`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:317`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:324`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:350`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:356`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:367`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:376`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:386`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:397`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:408`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:421`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:432`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:445`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:453`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:459`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:465`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:471`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:480`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:485`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:490`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:494`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:499`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:503`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:518`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:524`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:25`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:26`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:27`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:28`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:29`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:30`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:31`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:32`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:33`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:34`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:41`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:42`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:44`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:51`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:56`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:63`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:64`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:65`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:66`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:73`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:74`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:75`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:105`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:106`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:109`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:110`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:113`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:116`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:117`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:118`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:119`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:120`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:121`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:122`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:123`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:124`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:125`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:126`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:127`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:130`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:137`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:138`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:148`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:149`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:150`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:152`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:157`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:158`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:167`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:168`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:169`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:170`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:171`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:172`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:173`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:174`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:175`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:176`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:177`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:178`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:181`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:182`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:183`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:184`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:185`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:186`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:187`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:188`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:189`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:190`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:191`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:192`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:193`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:200`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:201`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:205`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:206`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:207`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:211`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:213`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:214`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:215`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:219`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:220`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:221`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:222`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:223`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:224`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:225`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:226`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:227`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:228`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:229`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:230`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:234`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:235`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:236`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:237`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:238`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:239`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:240`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:241`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:242`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:243`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:244`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:245`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:246`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:256`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:257`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:259`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:263`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:264`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:266`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:268`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:270`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:272`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:274`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:276`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:278`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:280`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:282`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:284`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:286`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:290`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:291`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:295`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:296`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:300`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:301`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:302`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:306`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:307`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:312`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:313`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:314`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:318`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:325`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:326`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:327`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:328`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:329`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:330`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:331`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:332`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:333`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:334`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:335`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:336`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:337`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:338`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:339`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:340`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:341`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:342`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:343`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:344`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:345`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:346`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:351`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:352`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:353`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:357`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:358`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:359`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:360`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:361`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:362`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:363`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:364`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:368`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:369`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:370`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:371`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:372`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:373`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:377`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:378`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:379`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:380`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:381`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:382`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:383`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:387`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:388`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:389`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:390`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:391`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:392`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:393`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:394`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:398`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:399`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:400`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:401`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:402`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:403`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:404`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:405`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:409`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:410`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:411`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:412`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:413`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:414`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:415`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:416`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:417`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:418`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:422`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:423`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:424`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:425`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:426`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:427`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:428`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:429`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:433`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:434`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:435`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:436`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:437`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:438`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:439`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:440`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:441`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:442`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:446`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:447`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:448`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:449`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:450`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:454`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:455`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:456`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:460`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:461`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:462`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:466`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:467`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:468`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:472`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:473`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:474`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:475`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:476`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:477`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:481`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:482`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:486`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:487`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:491`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:495`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:496`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:500`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:504`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:505`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:506`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:507`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:508`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:509`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:510`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:511`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:512`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:513`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:514`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:515`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:519`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:520`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:521`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:525`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:526`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:527`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:528`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:529`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:530`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:531`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:532`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:533`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:534`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:535`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeModels.kt:536`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:267`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:288`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:304`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:328`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:342`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:356`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:372`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:375`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:390`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:422`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:19`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:35`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:43`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:185`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:189`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:200`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:211`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:241`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:249`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:361`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:395`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:401`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:427`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:432`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:436`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:446`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:452`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:463`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:470`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:480`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:486`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:499`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:505`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:516`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:522`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:531`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:543`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:20`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:21`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:22`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:23`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:24`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:25`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:26`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:27`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:28`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:29`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:36`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:37`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:45`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:46`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:47`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:48`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:49`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:50`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:51`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:52`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:53`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:56`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:57`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:58`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:59`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:60`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:63`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:64`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:65`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:66`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:67`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:70`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:71`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:74`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:77`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:78`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:79`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:80`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:81`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:82`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:83`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:85`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:87`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:90`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:91`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:94`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:95`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:98`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:99`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:100`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:101`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:102`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:103`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:104`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:105`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:106`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:107`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:108`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:109`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:110`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:113`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:114`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:115`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:116`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:118`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:119`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:120`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:121`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:122`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:123`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:124`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:125`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:126`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:127`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:128`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:129`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:130`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:131`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:132`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:133`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:134`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:135`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:136`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:137`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:138`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:139`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:140`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:141`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:142`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:143`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:144`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:145`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:146`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:147`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:148`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:149`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:152`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:153`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:154`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:155`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:156`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:157`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:160`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:161`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:162`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:163`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:164`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:166`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:169`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:170`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:171`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:172`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:173`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:174`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:177`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:178`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:179`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:186`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:190`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:192`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:194`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:201`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:202`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:203`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:204`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:205`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:213`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:214`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:215`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:216`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:217`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:218`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:219`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:220`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:221`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:222`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:223`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:225`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:227`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:229`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:230`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:231`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:232`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:233`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:234`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:235`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:236`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:237`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:238`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:242`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:243`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:250`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:251`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:252`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:362`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:363`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:365`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:367`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:369`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:396`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:397`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:398`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:402`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:403`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:404`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:405`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:406`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:428`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:429`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:433`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:437`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:438`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:439`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:440`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:447`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:448`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:449`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:453`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:454`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:455`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:456`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:457`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:458`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:459`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:460`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:464`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:465`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:466`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:467`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:471`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:472`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:473`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:474`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:481`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:482`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:483`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:487`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:488`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:489`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:490`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:491`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:492`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:493`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:494`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:495`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:496`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:500`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:501`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:502`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:506`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:507`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:508`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:509`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:510`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:517`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:518`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:519`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:523`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:524`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:525`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:526`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:527`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:528`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:532`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:533`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:534`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:535`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:536`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:537`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:538`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:539`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:540`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:544`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:545`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:546`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeTokens.kt:547`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:52`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:86`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:102`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:123`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:156`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:191`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:208`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:227`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:256`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:22`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:23`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeThemeLoader.kt:24`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:86`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:144`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:34`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:35`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:36`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:37`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:38`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:171`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:177`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:183`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:189`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:195`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:201`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:207`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:213`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:219`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeTheme.kt:226`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:23`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:25`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:26`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:27`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:29`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:30`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:31`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:33`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:35`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:37`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:39`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:40`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:43`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:44`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:45`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:46`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:47`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:50`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:51`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:52`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:53`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:54`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:55`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:56`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:57`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:58`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:59`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:62`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:63`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:64`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:65`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:66`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:67`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:68`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:69`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:70`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:71`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:72`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:73`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:74`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:75`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:78`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:79`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:82`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:83`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:84`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:85`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:88`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:89`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:90`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:91`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:92`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:93`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:94`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:95`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:96`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:97`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:98`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:101`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:102`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:105`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:106`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:109`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:110`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:111`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:114`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:115`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:116`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:119`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:120`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:123`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:124`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:125`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeColors.kt:128`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:20`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:29`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:21`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:22`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:23`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:33`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:39`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:45`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:50`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:55`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/config/WelcomeConfig.kt:60`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/theme/ConciergeGradient.kt:37-50`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:208-246`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:2007-2042`.

#### CALLBACK_ONLY — CALLBACK_ONLY

Customer-implemented callback/interface contract. Invocation is not itself an EventHub producer. Its caller may independently publish the profiles documented for that caller. Content/observer interfaces are host implementation contracts. Concierge auth callback default timeout is 3000ms, clamped to 0..600000ms; missing/blank/throwing/timed-out provider degrades to no token, not an authentication event.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:34-57`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:39-79`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeDataHandoffCallback.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:46`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/PushNotificationListener.java:57`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:39`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:42`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:45`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:48`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:65`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateListener.kt:79`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateInterceptor.kt:44`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/ILiveUpdateStyleProvider.kt:32`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeAuthTokenProvider.kt:32`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChat.kt:208`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/ContentCardEventObserver.kt:68-93`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeAuthTokenHolder.kt:28-108`.

#### UI_INFRA — NO_EVENT

Shared UI construction/style/rendering infrastructure, not an extension facade. Platform notifications, PendingIntents, scheduling and UI callbacks are not EventHub publications; the injected host observer/tracking provider owns subsequent tracking.

Evidence: `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:80-115`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilderPlugin.kt:39-54`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/RemindLaterHandler.kt:41-90`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/ImageOnlyUI.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/LargeImageUI.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/SmallImageUI.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:41`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/AepUI.kt:49`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/BaseAepUI.kt:53`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:53`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:65`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:54`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIEvent.kt:55`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/InboxEvent.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/UIAction.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepButton.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepColor.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepIcon.kt:19`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepImage.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepInboxLayout.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepText.kt:19`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepText.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepUITemplate.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/AepUITemplate.kt:18`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/ImageOnlyTemplate.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:16`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:17`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:18`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:19`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/InboxTemplate.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:40`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/LargeImageTemplate.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:40`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/uimodels/SmallImageTemplate.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/AepCardUIState.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/ImageOnlyCardUIState.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/InboxUIState.kt:45`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/LargeImageCardUIState.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/state/SmallImageCardUIState.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:36`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:37`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:38`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:39`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:40`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:41`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepButtonStyle.kt:43`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepCardStyle.kt:36`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepColumnStyle.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepIconStyle.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepImageStyle.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:36`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:37`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyColumnStyle.kt:38`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:20`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:21`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:22`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:23`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:24`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepLazyRowStyle.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepRowStyle.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:29`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:32`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepTextStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:25`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/AepUIStyle.kt:28`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:62`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:63`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:64`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:65`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:68`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:56`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:35`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/ImageOnlyUIStyle.kt:36`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:90`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:92`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:94`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:96`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:99`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:102`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:105`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:108`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:110`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:113`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:116`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:78`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:41`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:43`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:45`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:46`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:47`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:48`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:49`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/InboxUIStyle.kt:50`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:125`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:126`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:127`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:128`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:129`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:130`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:131`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:132`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:133`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:134`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:137`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:113`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:43`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:45`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:46`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:47`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:48`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:49`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:50`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:51`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/LargeImageUIStyle.kt:52`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:125`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:126`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:127`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:128`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:129`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:130`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:131`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:132`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:133`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:134`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:137`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:113`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:43`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:44`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:45`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:46`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:47`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:48`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:49`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:50`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/style/SmallImageUIStyle.kt:51`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepInboxContentProvider.kt:26`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepInboxContentProvider.kt:31`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:27`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/contentprovider/AepUIContentProvider.kt:39`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/observers/AepInboxEventObserver.kt:34`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/observers/AepUIEventObserver.kt:30`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/AepInbox.kt:42`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/ImageOnlyCard.kt:37`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/LargeImageCard.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/SmallImageCard.kt:33`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/aepcomposeui/components/AepCircularProgressIndicator.kt:28`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:64`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:80`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilder.kt:106`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilderPlugin.kt:39`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationBuilderPlugin.kt:29`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:32`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationPriority.kt:40`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:31`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationVisibility.kt:40`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/RemindLaterHandler.kt:41`; `aepsdk-ui-android/code/notificationbuilder/src/main/java/com/adobe/marketing/mobile/notificationbuilder/NotificationConstructionFailedException.kt:19`.

#### CORE_PUSH_IDENTIFIER — DYNAMIC

MessagingService.onNewToken delegates to MobileCore.setPushIdentifier. This is a Core-owned API, not a Messaging-owned direct Event.Builder. Core and Messaging profile synchronization are configuration/identity gated; no fixed unconditional chain is asserted here.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:44-51`.

#### MSG_REFRESH — DIRECT

Concurrent refresh calls coalesce while updateInProgress; only the first dispatches. Callback completion releases the coalescing gate. See MSG_FETCH for the conditional downstream chain.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:396-399`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/IamRefreshHandler.kt:34-78`.

```json
{
  "eventName": "Refresh in-app messages",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "refreshmessages": true
  }
}
```

#### MSG_UPDATE — DIRECT

All four overloads share the same producer. Empty input or no valid mobileapp surfaces returns without dispatch or completion callback. Optional xdm/data keys are omitted when absent. Explicit updates are network-availability gated downstream; offline completion is false.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:543-661`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:519-540`.

```json
{
  "eventName": "Update propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "updatepropositions": true,
    "surfaces": [
      {
        "uri": "<surface-uri>"
      }
    ],
    "xdm": {
      "<customer-xdm-field>": "<safe-value>"
    },
    "data": {
      "<customer-data-field>": "<safe-value>"
    }
  }
}
```

#### MSG_FETCH — DOWNSTREAM_CONDITIONAL

Refresh/update -> Edge fetch; custom fields merge but fixed eventType and data.__adobe win. Edge stream response leads to finalize, not proof of decision/business success. Notification requires cached propositions. The three fixed schemas are expanded literally below.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:315-474`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:214-318`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:379-402`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:55-62`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:48-54`.

```json
{
  "eventName": "Retrieve message definitions",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "query": {
      "personalization": {
        "schemas": [
          "https://ns.adobe.com/personalization/html-content-item",
          "https://ns.adobe.com/personalization/json-content-item",
          "https://ns.adobe.com/personalization/ruleset-item"
        ],
        "surfaces": [
          "<surface-uri>"
        ]
      }
    },
    "xdm": {
      "eventType": "decisioning.propositionFetch",
      "<customer-xdm-field>": "<safe-value>"
    },
    "data": {
      "__adobe": {
        "ajo": {
          "in-app-response-format": 2
        }
      },
      "<customer-data-field>": "<safe-value>"
    },
    "request": {
      "sendCompletion": true
    }
  }
}
```

```json
{
  "eventName": "Finalize propositions response",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.contentComplete",
  "eventData": {
    "endingEventId": "<verified-edge-request-id>"
  }
}
```

#### MSG_GET — DIRECT_AND_RESPONSE

Null callback, empty inputs or no valid surfaces -> no dispatch. Cached lookup only, not a remote fetch. Offline-availability settings filter disk-origin cards. Empty response propositions are treated as unexpected error by facade. Error producer writes error.getErrorName(), but facade reads responseerror as int: do not normalize this source mismatch into a fictitious numeric contract.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:413-511`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:618-707`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InternalMessagingUtils.java:505-520`.

```json
{
  "eventName": "Get propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "getpropositions": true,
    "surfaces": [
      {
        "uri": "<surface-uri>"
      }
    ]
  }
}
```

```json
{
  "eventName": "Message propositions response",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "propositions": [
      {
        "id": "<proposition-id>",
        "scope": "<surface-uri>",
        "scopeDetails": {
          "<server-key>": "<safe-value>"
        },
        "items": [
          {
            "id": "<item-id>",
            "schema": "<schema-uri>",
            "data": {
              "<server-key>": "<safe-value>"
            }
          }
        ]
      }
    ]
  }
}
```

```json
{
  "eventName": "Message propositions response",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "responseerror": "<AdobeError.getErrorName()>"
  }
}
```

#### MSG_NOTIFICATION — DOWNSTREAM_CONDITIONAL

Only when cached propositions exist for the requested surfaces; not an unconditional response to each update. No builder parent/response linkage.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:575-608`.

```json
{
  "eventName": "Message propositions notification",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "propositions": [
      {
        "id": "<proposition-id>",
        "scope": "<surface-uri>",
        "scopeDetails": {
          "<server-key>": "<safe-value>"
        },
        "items": [
          {
            "id": "<item-id>",
            "schema": "<schema-uri>",
            "data": {
              "<server-key>": "<safe-value>"
            }
          }
        ]
      }
    ]
  }
}
```

#### MSG_CLEAR — DIRECT

Clears content-card memory/rules/origin and persisted card caches; not IAM/code-based caches. Fire-and-forget; no success response. Reviewed downstream clears in-memory content-card maps/rules and network-origin set, replaces content-card rules with an empty list, and clears the persisted content-card cache; it is not a blanket promise to delete every SDK cache.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:521-533`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:1633-1642`.

```json
{
  "eventName": "Clear cached propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "clearpersistedpropositions": true
  }
}
```

#### MSG_PUSH_RECEIVE — DIRECT

Received callback happens before validation. Missing message id or empty data -> no EventHub dispatch. adobe_xdm may be null. Service standard-push branch posts notification then self-initializes and calls trackPushReceived; live-update branch delegates to plugin instead.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:171-212`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingService.java:77-138`.

```json
{
  "eventName": "Push notification received",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "messageId": "<push-id>",
    "adobe_xdm": "<JSON-encoded-safe-xdm>",
    "eventType": "pushTracking.receive",
    "pushnotificationreceived": true
  }
}
```

#### MSG_PUSH_INTERACTION — DIRECT_AND_RESPONSE

Both overloads share producer; missing intent/id/tracking XDM -> local status callback without event. Empty customActionId selects applicationOpened; nonempty selects customAction and includes actionId. Response wait is 5000ms. Status 0 means TRACKING_INITIATED, not Edge delivery success. Response statuses 0..5 map to source enum. Empty customActionId selects applicationOpened eventType; nonempty selects customAction and includes actionId. The separate applicationOpened boolean can be true or false on either selector branch.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:224-389`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/InternalMessagingUtils.java:582-601`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PushTrackingStatus.java:20-36`.

```json
{
  "eventName": "Push notification interaction event",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "messageId": "<push-id>",
    "applicationOpened": true,
    "adobe_xdm": "<JSON-encoded-safe-xdm>",
    "eventType": "pushTracking.applicationOpened"
  }
}
```

```json
{
  "eventName": "Push notification interaction event",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "messageId": "<push-id>",
    "applicationOpened": false,
    "adobe_xdm": "<JSON-encoded-safe-xdm>",
    "eventType": "pushTracking.customAction",
    "actionId": "<custom-action-id>"
  }
}
```

```json
{
  "eventName": "Push tracking status event",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "pushTrackingStatus": 0,
    "pushTrackingStatusMessage": "Tracking initiated"
  }
}
```

#### MSG_PUSH_EDGE — DOWNSTREAM_CONDITIONAL

Requires configured messaging.eventDataset, valid eventType and messageId. mixins/cjm from JSON string merge into XDM and can replace initial values. application.launches.value is 1 or 0. Custom action absent on plain open/receive. Fixed push profile/channel is merged only on the matching CJM branch. Decisioning augmentation is separately conditional. If the decisioning map already contains exdRequestID, tracking adds propositionEventType.dismiss=1 when applicationOpened=false or interact=1 otherwise.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:479-496`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:811-898`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:1032-1164`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:132-158`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:1173-1229`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:132-158`.

pushTracking.receive; launches.value=0. Fixed CJM profile/channel appears only with a matching incoming CJM map; omit _experience when absent. Incoming mixins/cjm can overwrite initialized XDM values.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.receive",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>"
      },
      "application": {
        "launches": {
          "value": 0
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

pushTracking.applicationOpened; launches.value=1. Fixed CJM profile/channel appears only with a matching incoming CJM map; omit _experience when absent. Incoming mixins/cjm can overwrite initialized XDM values.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.applicationOpened",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>"
      },
      "application": {
        "launches": {
          "value": 1
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

pushTracking.applicationOpened; launches.value=0. Fixed CJM profile/channel appears only with a matching incoming CJM map; omit _experience when absent. Incoming mixins/cjm can overwrite initialized XDM values.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.applicationOpened",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>"
      },
      "application": {
        "launches": {
          "value": 0
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

pushTracking.customAction; launches.value=1. Fixed CJM profile/channel appears only with a matching incoming CJM map; omit _experience when absent. Incoming mixins/cjm can overwrite initialized XDM values.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.customAction",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>",
        "customAction": {
          "actionID": "<custom-action-id>"
        }
      },
      "application": {
        "launches": {
          "value": 1
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

pushTracking.customAction; launches.value=0. Fixed CJM profile/channel appears only with a matching incoming CJM map; omit _experience when absent. Incoming mixins/cjm can overwrite initialized XDM values.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.customAction",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>",
        "customAction": {
          "actionID": "<custom-action-id>"
        }
      },
      "application": {
        "launches": {
          "value": 0
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

Conditional augmentation when _experience.decisioning already contains exdRequestID. All tracking types pass this selector: applicationOpened=false adds dismiss:1; true adds interact:1, regardless of actionId. This includes receive tracking with the default false flag.

```json
{
  "eventName": "Push tracking edge event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.customAction",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<push-id>",
        "customAction": {
          "actionID": "<custom-action-id>"
        }
      },
      "application": {
        "launches": {
          "value": 0
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/push"
            }
          },
          "pushChannelContext": {
            "platform": "fcm"
          },
          "<server-correlation-key>": "<safe-value>"
        },
        "decisioning": {
          "exdRequestID": "<edge-request-id>",
          "propositionEventType": {
            "dismiss": 1
          }
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<configured-dataset-id>"
      }
    }
  }
}
```

#### MSG_PUSH_TO_INAPP — DOWNSTREAM_CONDITIONAL

Nonempty adb_iam_id -> asynchronous update for app surface -> rule-engine event even when update callback false. This branch is independent of push-interaction tracking status.

Evidence: `aepsdk-messaging-android/code/messaging/src/phone/java/com/adobe/marketing/mobile/Messaging.java:285-331`.

```json
{
  "eventName": "Push to in-app",
  "eventType": "com.adobe.eventType.rulesEngine",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "adb_iam_id": "<inapp-id>"
  }
}
```

#### MSG_TRACK_ITEM — DIRECT

ContentCard/FeedItem -> parent schema -> PropositionItem.track; absent parent/reference or unavailable interaction XDM can suppress dispatch. History write occurs before interaction generation when proposition reference exists. generateInteractionXdm alone is NO_EVENT. Tokens joined by comma. Interaction action has id/label only for INTERACT, reason only for SUPPRESS_DISPLAY. The two deprecated push enum constants pass through to pushTracking.applicationOpened/customAction and produce an empty-string proposition marker; no invented decisioning mapping is substituted.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionItem.java:112-206`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionInteraction.java:60-133`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionInteractionXdmUtils.java:36-79`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:14-99`.

decisioning.propositionTrigger. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionTrigger",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "trigger": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionDisplay. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionDisplay",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "display": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionInteract. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionInteract",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "interact": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ],
          "propositionAction": {
            "id": "<interaction>",
            "label": "<interaction>"
          }
        }
      }
    }
  }
}
```

decisioning.propositionDismiss. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionDismiss",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "dismiss": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionDisqualify. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionDisqualify",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "disqualify": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionSuppressDisplay. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "decisioning.propositionSuppressDisplay",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "suppressDisplay": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ],
          "propositionAction": {
            "reason": "<suppression-reason>"
          }
        }
      }
    }
  }
}
```

Deprecated push enum compatibility: empty proposition marker; pushTracking.applicationOpened. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "pushTracking.applicationOpened",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

Deprecated push enum compatibility: empty proposition marker; pushTracking.customAction. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Track propositions",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "trackpropositions": true,
    "propositioninteraction": {
      "eventType": "pushTracking.customAction",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

#### MSG_TRACK_EDGE — DIRECT_OR_DOWNSTREAM

Message.track -> direct Edge event (does NOT first emit Track propositions). Item tracking -> Messaging listener -> Edge; may enrich content-card display origin. Event types: decisioning.propositionTrigger/Display/Interact/Dismiss/Disqualify/SuppressDisplay; their marker keys trigger/display/interact/dismiss/disqualify/suppressDisplay carry 1. Message path omits item list. Null eventType/propositionInfo/extension -> no dispatch. Builder parent is null. The two deprecated push enum constants pass through to pushTracking.applicationOpened/customAction and produce an empty-string proposition marker; no invented decisioning mapping is substituted.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PresentableMessageMapper.java:270-308`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:550-568`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingExtension.java:906-917`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:59-99`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/MessagingEdgeEventType.java:14-99`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/EdgePersonalizationResponseHandler.java:1487-1601`.

decisioning.propositionTrigger. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionTrigger",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "trigger": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionDisplay. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionDisplay",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "display": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionInteract. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionInteract",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "interact": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ],
          "propositionAction": {
            "id": "<interaction>",
            "label": "<interaction>"
          }
        }
      }
    }
  }
}
```

decisioning.propositionDismiss. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionDismiss",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "dismiss": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionDisqualify. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionDisqualify",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "disqualify": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

decisioning.propositionSuppressDisplay. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionSuppressDisplay",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "suppressDisplay": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ],
          "propositionAction": {
            "reason": "<suppression-reason>"
          }
        }
      }
    }
  }
}
```

Deprecated push enum compatibility: empty proposition marker; pushTracking.applicationOpened. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.applicationOpened",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

Deprecated push enum compatibility: empty proposition marker; pushTracking.customAction. Optional action requires nonempty interaction; optional tokens require a nonempty token list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "pushTracking.customAction",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              }
            }
          ]
        }
      }
    }
  }
}
```

Item DISPLAY forwarding example, only for currently qualified content cards; servedFromPersistentCache is true when the surface has not been network-refreshed in this session, false otherwise. Other item interaction types retain items/token data but receive no origin enrichment. Message.track omits the item list.

```json
{
  "eventName": "Messaging interaction event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "decisioning.propositionDisplay",
      "_experience": {
        "decisioning": {
          "propositionEventType": {
            "display": 1
          },
          "propositions": [
            {
              "id": "<proposition-id>",
              "scope": "<surface-uri>",
              "scopeDetails": {
                "<server-key>": "<safe-value>"
              },
              "items": [
                {
                  "id": "<item-id>",
                  "characteristics": {
                    "tokens": "<comma-joined-tokens>"
                  },
                  "data": {
                    "characteristics": {
                      "servedFromPersistentCache": true
                    }
                  }
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```

#### MSG_HISTORY — LOCAL_EVENTHUB

No activity id -> no dispatch. recordDisplay writes only history; it does not send an Edge display event. Mask: iam.eventType, iam.id, iam.action. EventHub/event-history record is not a network request. iam.eventType is the enum marker (trigger/display/interact/dismiss/disqualify/suppressDisplay; empty for the two deprecated push constants), not the full decisioning.* event type.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PresentableMessageMapper.java:311-379`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PropositionHistory.java:37-78`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingConstants.java:413-425`.

```json
{
  "eventName": "Write IAM event to history",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.eventHistoryWrite",
  "eventData": {
    "iam": {
      "eventType": "display",
      "id": "<activity-id>",
      "action": ""
    }
  }
}
```

#### MSG_PRESENT — CONDITIONAL_BEHAVIOR

show/dismiss delegates to AEP UI. Resulting fullscreen lifecycle callbacks can record MSG_HISTORY and, when autoTrack true, MSG_TRACK_EDGE. No unconditional synchronous event is promised. Presentation delegates may suppress show; setAutoTrack changes future behavior only.

Evidence: `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/PresentableMessageMapper.java:319-357`; `aepsdk-messaging-android/code/messaging/src/main/java/com/adobe/marketing/mobile/messaging/MessagingFullscreenEventListener.java:43-160`.

#### ASSURANCE_URL — DIRECT

Facade only checks nonnull URL containing adb_validation_sessionid. Listener further validates session id, active-session absence and application context before launching PIN UI/session orchestration. Dispatch is not connection success; no fixed EventHub response.

Evidence: `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:54-80`; `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/assurance/internal/AssuranceExtension.java:135-207`; `aepsdk-core-android/code/core/src/main/java/com/adobe/marketing/mobile/EventType.java:28`.

```json
{
  "eventName": "Assurance Start Session",
  "eventType": "com.adobe.eventType.assurance",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "startSessionURL": "<safe-assurance-deeplink-with-adb_validation_sessionid>"
  }
}
```

#### ASSURANCE_QUICK — DIRECT

Facade always dispatches; extension rejects non-debuggable or absent application and existing session. UI/WebSocket transport is not an EventHub response.

Evidence: `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/Assurance.java:87-98`; `aepsdk-assurance-android/code/assurance/src/main/java/com/adobe/marketing/mobile/assurance/internal/AssuranceExtension.java:214-230`.

```json
{
  "eventName": "Assurance Start Session (Quick Connect)",
  "eventType": "com.adobe.eventType.assurance",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "quickConnect": true
  }
}
```

#### LIVE_RECEIVE — DIRECT_CONDITIONAL

Parsed canonical start/update/end plus nonnull _xdm required for Edge tracking. localstart records local state without immediate Edge dispatch; one later correlated backend update/end can emit catch-up localstart with original XDM timestamp. Dataset override optional, only after configuration supplied one. Initial SDK XDM is merged with incoming mixins/cjm (or flattened incoming map).

Evidence: `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:275-285`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:525-701`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:791-875`.

liveupdate_start; dataset meta is optional.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.received",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "liveupdate_start"
            }
          }
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<optional-configured-dataset>"
      }
    }
  }
}
```

liveupdate_update; dataset meta is optional.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.received",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "liveupdate_update"
            }
          }
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<optional-configured-dataset>"
      }
    }
  }
}
```

liveupdate_end; dataset meta is optional.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.received",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "liveupdate_end"
            }
          }
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<optional-configured-dataset>"
      }
    }
  }
}
```

liveupdate_localstart is delayed catch-up only, not immediate localstart tracking.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.received",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "liveupdate_localstart"
            }
          }
        }
      }
    },
    "meta": {
      "collect": {
        "datasetId": "<optional-configured-dataset>"
      }
    }
  }
}
```

#### LIVE_INTERACTION — DIRECT_CONDITIONAL

False only for null/no notification-id intent. True does not prove a dispatch: malformed/missing XDM or neither open nor custom action suppresses it. Nonempty customActionId wins over applicationOpened; dismiss uses actionID Dismiss. liveActivity.event is omitted on interactions; meta override optional.

Evidence: `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:374-405`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:627-701`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:791-875`.

Application opened with no nonempty custom action.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.applicationOpened",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>"
            }
          }
        }
      }
    }
  }
}
```

Nonempty custom action wins over applicationOpened.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.customAction",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>",
        "customAction": {
          "actionID": "<custom-action-id>"
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>"
            }
          }
        }
      }
    }
  }
}
```

Fixed dismissal action identifier. All interaction variants omit liveActivity.event; configured dataset meta remains optional.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.customAction",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>",
        "customAction": {
          "actionID": "Dismiss"
        }
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>"
            }
          }
        }
      }
    }
  }
}
```

#### LIVE_TOPIC — DIRECT_CONDITIONAL

Tracks only; does not subscribe/unsubscribe FCM. Empty payload.topicName or missing xdm -> no dispatch. Same XDM construction/optional meta as LIVE_RECEIVE, with payload notification id retained.

Evidence: `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:423-488`.

topic_subscribed; reports subscription state only; it does not perform an FCM subscription.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.topic",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name>",
              "liveActivityID": "<notification-id>",
              "event": "topic_subscribed"
            }
          }
        }
      }
    }
  }
}
```

topic_unsubscribed; reports subscription state only; it does not perform an FCM subscription.

```json
{
  "eventName": "Live Update Event Tracking",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.topic",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name>",
              "liveActivityID": "<notification-id>",
              "event": "topic_unsubscribed"
            }
          }
        }
      }
    }
  }
}
```

#### LIVE_RENDER — CONDITIONAL_BEHAVIOR

Plugin renders Android notification, invokes lifecycle hooks and conditionally LIVE_RECEIVE/LIVE_DIAGNOSTIC. triggerLocalLiveUpdate returns false without canonical plugin; true means plugin.postLiveUpdate ran, not successful promotion/posting. Interceptor rejection and render checks can still suppress notification.

Evidence: `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:310-324`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdatePlugin.kt:56-220`.

#### LIVE_DIAGNOSTIC — LOCAL_EVENTHUB

Render-error/incompatibility branches only, not Edge experiences. Same live-activity XDM construction as tracking; no dataset meta. renderError event labels: app_discarded, notification_permission_missing, style_null, invalid_timestamp, outdated_timestamp, invalid_event_type. incompatible labels: device_api_below_36, not_promotable, notification_manager_unavailable, channel_not_registered, channel_importance_low, promotion_not_permitted. A comment mentioning no_plugin does not implement that event.

Evidence: `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:97-129`; `aepsdk-liveupdates-android/code/liveupdates/src/main/java/com/adobe/marketing/mobile/messaging/liveupdate/LiveUpdates.kt:721-776`.

Conditional diagnostic: app_discarded. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "app_discarded"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: notification_permission_missing. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "notification_permission_missing"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: style_null. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "style_null"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: invalid_timestamp. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "invalid_timestamp"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: outdated_timestamp. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "outdated_timestamp"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: invalid_event_type. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Render Error",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.renderError",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "invalid_event_type"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: device_api_below_36. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "device_api_below_36"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: not_promotable. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "not_promotable"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: notification_manager_unavailable. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "notification_manager_unavailable"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: channel_not_registered. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "channel_not_registered"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: channel_importance_low. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "channel_importance_low"
            }
          }
        }
      }
    }
  }
}
```

Conditional diagnostic: promotion_not_permitted. It does not by itself establish that notify/track/listener execution was aborted.

```json
{
  "eventName": "Live Update Incompatible",
  "eventType": "com.adobe.eventType.messaging",
  "eventSource": "com.adobe.eventSource.errorResponseContent",
  "eventData": {
    "xdm": {
      "eventType": "liveUpdateTracking.incompatible",
      "pushNotificationTracking": {
        "pushProvider": "fcm",
        "pushProviderMessageID": "<notification-id>"
      },
      "_experience": {
        "customerJourneyManagement": {
          "messageProfile": {
            "channel": {
              "_id": "https://ns.adobe.com/xdm/channels/liveactivity"
            }
          },
          "pushChannelContext": {
            "platform": "fcm",
            "liveActivity": {
              "channelID": "<topic-name-or-empty>",
              "liveActivityID": "<notification-id>",
              "event": "promotion_not_permitted"
            }
          }
        }
      }
    }
  }
}
```

#### CON_HANDOFF — DIRECT_AND_RESPONSE

All JVM overloads use one sender. Event-level validation rejects empty/non-JSON/identityMap-colliding XDM. Requires mounted active chat session; busy, no-session, delivery-failed, empty-response and timeout branches report rejection. localMessage optional. Request response wait 65000ms; delivery timeout 60000ms; first-chunk timeout 10000ms. InResponseToEvent links response. HTTP/SSE chat transport is not another EventHub event. Handoff execution does not submit a typed user QuerySubmitted event. It can render streamed responses and encounter the separately documented response/card/error notification paths; those are not unconditional handoff events.

Callback-local outcomes: {"no_response": "No extension response event; translated locally to the callback enum NO_RESPONSE."}.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/Concierge.kt:104-112`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeDataHandoffSender.kt:28-59`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeDataHandoffEventHandler.kt:36-99`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:80-145`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ui/chat/ConciergeChatViewModel.kt:1231-1347`.

Request; localMessage is optional.

```json
{
  "eventName": "Concierge Data Handoff Event",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "routingHint": "<routing-hint-or-empty>",
    "xdmFields": {
      "<customer-field>": "<safe-value>"
    },
    "localMessage": "<optional-display-text>"
  }
}
```

Delivered result; not merely the arrival of any response.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": true
  }
}
```

Conditional extension rejection: missing_event_data.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "missing_event_data"
  }
}
```

Conditional extension rejection: invalid_routing_hint_type.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "invalid_routing_hint_type"
  }
}
```

Conditional extension rejection: missing_xdm_fields.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "missing_xdm_fields"
  }
}
```

Conditional extension rejection: invalid_xdm_fields_type.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "invalid_xdm_fields_type"
  }
}
```

Conditional extension rejection: empty_xdm_fields.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "empty_xdm_fields"
  }
}
```

Conditional extension rejection: invalid_xdm_field_key.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "invalid_xdm_field_key"
  }
}
```

Conditional extension rejection: reserved_key_collision.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "reserved_key_collision"
  }
}
```

Conditional extension rejection: invalid_xdm_field_value.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "invalid_xdm_field_value"
  }
}
```

Conditional extension rejection: no_active_session.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "no_active_session"
  }
}
```

Conditional extension rejection: chat_in_progress.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "chat_in_progress"
  }
}
```

Conditional extension rejection: delivery_failed.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "delivery_failed"
  }
}
```

Conditional extension rejection: empty_response.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "empty_response"
  }
}
```

Conditional extension rejection: delivery_timeout.

```json
{
  "eventName": "Concierge Data Handoff Event Response",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "accepted": false,
    "rejectReason": "delivery_timeout"
  }
}
```

#### CON_SESSION_INITIALIZED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Session Initialized",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:session:initialized"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:session:initialized"
    },
    "data": {
      "conciergeEventType": "concierge:session:initialized"
    }
  }
}
```

#### CON_CHAT_OPENED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Chat Opened",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:chat:opened",
    "epochTime": "<epoch-millis>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:chat:opened"
    },
    "data": {
      "epochTime": "<epoch-millis>",
      "conciergeEventType": "concierge:chat:opened"
    }
  }
}
```

#### CON_CHAT_CLOSED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Chat Closed",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:chat:closed",
    "epochTime": "<epoch-millis>",
    "durationMillis": "<duration-millis>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:chat:closed"
    },
    "data": {
      "epochTime": "<epoch-millis>",
      "durationMillis": "<duration-millis>",
      "conciergeEventType": "concierge:chat:closed"
    }
  }
}
```

#### CON_CONVERSATION_ENDED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Conversation Ended",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:conversation:ended",
    "epochTime": "<epoch-millis>",
    "reason": "identity_reset",
    "hadActiveTurn": false,
    "sessionId": "<optional-session-id>",
    "conversationId": "<optional-conversation-id>"
  }
}
```

#### CON_QUERY_SUBMITTED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Query Submitted",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:query:submitted",
    "query": "<query-text>",
    "xdmFields": {
      "<customer-field>": "<safe-value>"
    }
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:query:submitted"
    },
    "data": {
      "conciergeEventType": "concierge:query:submitted"
    }
  }
}
```

#### CON_PROMPT_SUGGESTION_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Prompt Suggestion Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:promptSuggestion:clicked",
    "suggestion": "<suggestion-text>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:promptSuggestion:clicked"
    },
    "data": {
      "suggestion": "<suggestion-text>",
      "conciergeEventType": "concierge:promptSuggestion:clicked"
    }
  }
}
```

#### CON_WELCOME_PROMPT_SUGGESTION_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Welcome Prompt Suggestion Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:welcomePromptSuggestion:clicked",
    "suggestion": "<suggestion-text>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:welcomePromptSuggestion:clicked"
    },
    "data": {
      "suggestion": "<suggestion-text>",
      "conciergeEventType": "concierge:welcomePromptSuggestion:clicked"
    }
  }
}
```

#### CON_CARD_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Card Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:card:clicked",
    "element": {
      "productName": "<product-name>",
      "productPageURL": "<safe-product-url>",
      "<other-server-field>": "<safe-value>"
    }
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:card:clicked"
    },
    "data": {
      "element": {
        "productName": "<product-name>",
        "productPageURL": "<safe-product-url>"
      },
      "conciergeEventType": "concierge:card:clicked"
    }
  }
}
```

#### CON_MIC_BUTTON_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Mic Button Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:micButton:clicked"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:micButton:clicked"
    },
    "data": {
      "conciergeEventType": "concierge:micButton:clicked"
    }
  }
}
```

#### CON_RESPONSE_STARTED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Response Started",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:response:started",
    "conversationId": "<conversation-id>",
    "interactionId": "<interaction-id>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:response:started"
    },
    "data": {
      "conversationId": "<conversation-id>",
      "interactionId": "<interaction-id>",
      "conciergeEventType": "concierge:response:started"
    }
  }
}
```

#### CON_RESPONSE_COMPLETED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Response Completed",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:response:completed",
    "conversationId": "<conversation-id>",
    "interactionId": "<interaction-id>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:response:completed"
    },
    "data": {
      "conversationId": "<conversation-id>",
      "interactionId": "<interaction-id>",
      "conciergeEventType": "concierge:response:completed"
    }
  }
}
```

#### CON_CARDS_RENDERED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Cards Rendered",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:cards:rendered",
    "displayMode": "<display-mode>",
    "elements": [
      {
        "productName": "<product-name>",
        "productPageURL": "<safe-product-url>",
        "<other-server-field>": "<safe-value>"
      }
    ]
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:cards:rendered"
    },
    "data": {
      "displayMode": "<display-mode>",
      "elements": [
        {
          "productName": "<product-name>",
          "productPageURL": "<safe-product-url>"
        }
      ],
      "conciergeEventType": "concierge:cards:rendered"
    }
  }
}
```

#### CON_FEEDBACK_SUBMITTED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Feedback Submitted",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:feedback:submitted",
    "conversationId": "<conversation-id>",
    "interactionId": "<interaction-id>",
    "feedbackType": "<feedback-type>",
    "selectedOptions": [
      "<option>"
    ],
    "notes": "<feedback-notes>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:feedback:submitted"
    },
    "data": {
      "conversationId": "<conversation-id>",
      "interactionId": "<interaction-id>",
      "feedbackType": "<feedback-type>",
      "selectedOptions": [
        "<option>"
      ],
      "conciergeEventType": "concierge:feedback:submitted"
    }
  }
}
```

#### CON_ERROR_OCCURRED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Error Occurred",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:error:occurred",
    "errorMessage": "<safe-error-message>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:error:occurred"
    },
    "data": {
      "errorMessage": "<safe-error-message>",
      "conciergeEventType": "concierge:error:occurred"
    }
  }
}
```

#### CON_DISCLAIMER_LINK_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge Disclaimer Link Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:disclaimerLink:clicked",
    "url": "<safe-url>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:disclaimerLink:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "conciergeEventType": "concierge:disclaimerLink:clicked"
    }
  }
}
```

#### CON_CTA_BUTTON_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

```json
{
  "eventName": "Brand Concierge CTA Button Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:ctaButton:clicked",
    "url": "<safe-url>",
    "label": "<label>"
  }
}
```

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:ctaButton:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "label": "<label>",
      "conciergeEventType": "concierge:ctaButton:clicked"
    }
  }
}
```

#### CON_LINK_CLICKED — CONDITIONAL_UI_NOTIFICATION

UI/lifecycle occurrence, not an unconditional API response. Notification uses no parent/response link. Edge forwarding defaults disabled and requires setEdgeTrackingEnabled(true); typed fields are filtered. Conversation-ended never forwards.

Evidence: `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeTrackingEvent.kt:89-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeConstants.kt:149-212`; `aepsdk-concierge-android/code/concierge/src/main/kotlin/com/adobe/marketing/mobile/concierge/ConciergeEventTracker.kt:56-276`.

Link origin citation; notification from the selected UI action.

```json
{
  "eventName": "Brand Concierge Link Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:link:clicked",
    "url": "<safe-url>",
    "origin": "citation"
  }
}
```

Link origin citation; optional Edge forwarding only when tracking is enabled.

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:link:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "origin": "citation",
      "conciergeEventType": "concierge:link:clicked"
    }
  }
}
```

Link origin linkHint; notification from the selected UI action.

```json
{
  "eventName": "Brand Concierge Link Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:link:clicked",
    "url": "<safe-url>",
    "origin": "linkHint"
  }
}
```

Link origin linkHint; optional Edge forwarding only when tracking is enabled.

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:link:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "origin": "linkHint",
      "conciergeEventType": "concierge:link:clicked"
    }
  }
}
```

Link origin inline; notification from the selected UI action.

```json
{
  "eventName": "Brand Concierge Link Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:link:clicked",
    "url": "<safe-url>",
    "origin": "inline"
  }
}
```

Link origin inline; optional Edge forwarding only when tracking is enabled.

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:link:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "origin": "inline",
      "conciergeEventType": "concierge:link:clicked"
    }
  }
}
```

Link origin productCard; notification from the selected UI action.

```json
{
  "eventName": "Brand Concierge Link Clicked",
  "eventType": "com.adobe.eventType.concierge",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "conciergeEventType": "concierge:link:clicked",
    "url": "<safe-url>",
    "origin": "productCard"
  }
}
```

Link origin productCard; optional Edge forwarding only when tracking is enabled.

```json
{
  "eventName": "Concierge Tracking Edge Request",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "concierge:link:clicked"
    },
    "data": {
      "url": "<safe-url>",
      "origin": "productCard",
      "conciergeEventType": "concierge:link:clicked"
    }
  }
}
```

### Coverage ledger

Customer API inventoried/documented: **951/951**. Supporting infrastructure: **267/267**, not counted as customer extension APIs. Profiles: **43**.

| Customer section | Eligible entries |
|---|---:|
| Messaging | 139 |
| Messaging UI integration | 26 |
| Assurance | 3 |
| Live Updates (class library/plugin) | 48 |
| Concierge | 12 |
| Concierge chat UI | 17 |
| Concierge customer theme/configuration models | 706 |

Customer kinds: method=198, constructor=98, callback=17, property=638.

Count domains: {"eligibleCustomerApis": 951, "customerFacadesModelsCallbacksBehaviorExcludingThemeGraph": 245, "customerThemeConfigurationApis": 706, "supportingInfrastructureDeclarations": 267, "excludedImplementationDeclarations": 22, "invalidScannerCandidatesRemoved": 9, "allRetainedDeclarationRowsDocumented": 1240}.

All API and supporting inventory IDs occur above. Accessor contracts count once per property; explicit JVM overloads count separately. No unresolved source-access gaps remain. This is a source inventory and role audit, not a compiled ABI audit; no checked-in baseline/build output is claimed.

Exclusions:
* Anonymous callbacks/delegates inside Messaging and UI observers; eight invalid scanner rows removed.
* @JvmField EXTENSION and other fields/constants are not callable APIs.
* Extension subclasses, SDK-internal caches/mappers/engines/listeners/renderers, tests/samples and telemetry-bridge implementation.
* FCM service platform/host integration, the Core-invoked Live Update plugin callback, shared Compose and NotificationBuilder surfaces are documented separately in infrastructureInventory, not customer apiInventory.
* ConciergeThemeLoader instance helpers have a private constructor and only an internal instance accessor; the three public companion helpers remain customer APIs.
* ActiveConciergeTheme is a private-CompositionLocal state holder; WelcomeConfig/SuggestedPrompt feed internal ViewModel welcome state, not the public theme configuration graph.
* Package-private ContentCard.Builder, schema constructors and MessageRequiredFieldMissingException are excluded; deprecated public FeedItem.Builder is retained.
* Internal low-level Compose components/previews, sealed constructors, inherited methods, compiler synthetic operations and non-callable constants are excluded.

---

<a id="family-edge-family"></a>

## Edge-family customer API → EventHub catalog

**Summary:** Four checkout snapshots expose 63 inventoried customer callables: Edge 34, Edge Identity 25, Consent 3, Edge Bridge 1. Models, overloads, callbacks, XDM contracts and local metadata are included, not just network facades.

### Snapshot and evidence

All repositories were reviewed on `poc/telemetry-integration`, **not a released-SDK baseline**:

| Repository | HEAD | Source status |
|---|---|---|
| aepsdk-edge-android | `b10baf2515eda20a6cb78b8c424b85c3b6947fb2` | Test-app/build changes; no tracked Edge library source changes reported; untracked docs/.scout |
| aepsdk-edgeidentity-android | `f842ea5ae8db42d698eed619bebb027bb04084dc` | Test-app/resources/settings changes; no tracked Identity library source changes reported; untracked .scout |
| aepsdk-edgeconsent-android | `6c384b2b9bd5f0dc7de2bf5eea6db7c4f2acfce4` | No tracked changes reported; untracked .scout |
| aepsdk-edgebridge-android | `155e4c7df1a2fa3d340e62fe60cf23eba995755f` | Test-app changes; no tracked Bridge library source changes reported; untracked .scout/test image |

Scout served all source. Consent/Bridge attachment succeeded once; no UNINDEXED fallback was necessary. Blocking `ensure_fresh` verified tracked manifests with zero reindexed gap files in all four. Derived graph/vector freshness was not certified. Some lexical snippets had different line numbers than source reads: **source-range bodies take precedence**. Source-state IDs are retained in JSON.

Core dependency evidence is from HEAD `3a2eb50e437742e9738222bcc7e9099c4af32481`, branch `poc/telemetry-integration`; no tracked changes reported (three untracked TELEMETRY documentation files). Its reviewed scope is constants, link semantics and the operational rules consequence, not another extension inventory.

#### Completed public-surface reconciliation

A different read-only completion check enumerated **filenames from Git index/worktree metadata**, not source text: `git ls-files --cached --others --exclude-standard -z -- code/<library>/src/`. This established the complete candidate-file census, including non-ignored untracked files. Scout directory/file outlines then supplied type/member anchors; bounded native reads verified those exact anchors and previously Scout-returned customer declaration ranges. The refused regex operation was **not retried**, and no source-content discovery scan was substituted.

The only source sets present in this Java/Kotlin filename census are `main`, `test`, and `androidTest`; the latter two are excluded by contract. No additional production source set remains unaccounted for.

| Repository | Production `main` files | Customer declaration files | Nonpublic/internal files | Explicit public infrastructure files | Excluded test / androidTest files |
|---|---:|---:|---:|---:|---:|
| Edge | 49 | 9 | 38 | 2 | 34 / 14 |
| Edge Identity | 21 | 4 | 16 | 1 | 17 / 7 |
| Consent | 7 | 1 | 5 | 1 | 6 / 5 |
| Edge Bridge | 5 | 1 | 3 | 1 | 5 / 1 |
| **Total** | **82** | **15** | **62** | **5** | **62 / 27** |

`edge-family.json → surfaceReconciliation.productionFileCensus` records **every production filename**, its package-directory prefix, and the declaration anchor for each excluded nonpublic/internal file. All 62 excluded implementation files have a package-private Java top-level owner or Kotlin `internal` owner, so public overrides or nested declarations inside them do not create an accessible customer type. Java files have one public top-level owner at most; the two Kotlin production files were separately reconciled, including both types in `SDKConfig.kt`.

##### File-by-file callable accounting

The 15 customer files reconcile to **62 explicitly declared callables + 1 implicit constructor = 63 inventory rows**, with no unmatched callable name or overload multiplicity. Interface methods are counted despite implicit `public`; anonymous implementation callbacks are not counted as new customer declarations.

| Customer source file (canonical source key) | Explicit callable declarations | Additional implicit constructor | Inventory IDs |
|---|---:|---:|---|
| Edge.java (E) | 5 | 0 | E01–E05 |
| ExperienceEvent.java (EE), including public Builder | 12 | 0 | E06–E17 |
| EdgeCallback.java (ECB) | 1 | 0 | E18 |
| EdgeCallbackWithError.java (ECBE) | 2 | 0 | E19–E20 |
| EdgeEventHandle.java (EH) | 3 | 0 | E21–E23 |
| EdgeEventError.java (ER) | 5 | 0 | E24–E28 |
| xdm/Schema.java (XS) | 4 | 0 | E29–E32 |
| xdm/Property.java (XP) | 1 | 0 | E33 |
| xdm/Formatters.java (XF) | 1 | 0 | E34 |
| Identity.java (I) | 7 | 0 | I01–I07 |
| IdentityMap.java (IM) | 6 | 1 | I08–I14 |
| IdentityItem.java (II) | 9 | 0 | I15–I23 |
| AuthenticatedState.java (AS) | 2 | 0 | I24–I25 |
| Consent.java (C) | 3 | 0 | C01–C03 |
| EdgeBridge.java (B) | 1 | 0 | B01 |
| **Total** | **62** | **1** | **63 unique IDs** |

This includes both sendEvent overloads, all three setXdmSchema overloads and all three IdentityItem constructors. IdentityMap's trailing `addItemToMap` / `removeItemFromMap` at `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityMap.java:329,363` are private, not omitted public methods.

##### Explicitly accounted public infrastructure

These are **public**, not silently classified as package-private. They remain outside the supplied customer-facade/model policy as SDK implementation/integration infrastructure:

* `code/edge/src/main/java/com/adobe/marketing/mobile/edge/SDKConfig.kt:18-37` declares `SDKConfig(datastream: Datastream)` and `Datastream(original: String)`. Each declares `fun toMap(): MutableMap<String, Any>` at lines 19 and 31. Public readonly properties `val datastream: Datastream` and `val original: String` generate JVM `getDatastream()` / `getOriginal()`. Generated data-class methods are not separately counted. These types encode original datastream configuration in SDK-created network request metadata, not a public customer facade parameter/return contract. Scout's caller evidence identifies `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeHitProcessor.java:550` and `code/edge/src/main/java/com/adobe/marketing/mobile/RequestBuilder.java:32,97`.
* Each of the four `code/<library>/src/main/java/com/adobe/marketing/mobile/telemetrybridge/TelemetryBridge.java` files declares `public static String operationStarted(String, String)` at line 16 and `public static void operationCompleted(String, String, String, String, String)` at line 26. Their constructors are private. These are optional POC reflection adapters to the telemetry plugin, not extension customer facades; all four matching declaration/body ranges were verified. Exact paths are listed in the JSON infrastructure accounting.

**Unaccounted customer files: none. Unaccounted customer signatures: none.** An independent ABI baseline was not read; that is an **ABI/release-certification limit only**, not a source declaration omission, payload gap or source-access gap. Earlier instruction-name searches returned no content; no on-disk absence of AGENTS.md is inferred.

#### Canonical source keys

Inventory references below use these exact paths plus line numbers; the manifest repeats full paths for each API.

| Key | Repository | Exact path |
|---|---|---|
| E | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/Edge.java` |
| EE | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/ExperienceEvent.java` |
| ECB | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeCallback.java` |
| ECBE | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeCallbackWithError.java` |
| EH | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeEventHandle.java` |
| ER | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeEventError.java` |
| XS | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/xdm/Schema.java` |
| XP | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/xdm/Property.java` |
| XF | aepsdk-edge-android | `code/edge/src/main/java/com/adobe/marketing/mobile/xdm/Formatters.java` |
| I | aepsdk-edgeidentity-android | `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/Identity.java` |
| IM | aepsdk-edgeidentity-android | `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityMap.java` |
| II | aepsdk-edgeidentity-android | `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityItem.java` |
| AS | aepsdk-edgeidentity-android | `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/AuthenticatedState.java` |
| C | aepsdk-edgeconsent-android | `code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/Consent.java` |
| B | aepsdk-edgebridge-android | `code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridge.java` |

### Explicit API / overload inventories

Each row names one or more JSON profile IDs. `*.local` means **NO_EVENT**, not an omitted mapping. `edge.callback` means **CALLBACK_ONLY**. Conditional downstream profiles are not unconditional method effects.

#### Edge — 34 callables

| ID | Class | Signature | Source | Profiles |
|---|---|---|---|---|
| E01 | Edge | `public static String extensionVersion()` | E:39 | edge.local |
| E02 | Edge | `public static void sendEvent(ExperienceEvent, EdgeCallback)` | E:50 | edge.request; edge.responses |
| E03 | Edge | `public static void sendEvent(ExperienceEvent, EdgeCallbackWithError)` | E:102 | edge.request; edge.responses |
| E04 | Edge | `public static void getLocationHint(AdobeCallback<String>)` | E:120 | edge.location.get |
| E05 | Edge | `public static void setLocationHint(String)` | E:207 | edge.location.set |
| E06 | ExperienceEvent.Builder | `public Builder()` | EE:51 | edge.local |
| E07 | ExperienceEvent.Builder | `public Builder setDatastreamIdOverride(String)` | EE:66 | edge.local |
| E08 | ExperienceEvent.Builder | `public Builder setDatastreamConfigOverride(Map<String, Object>)` | EE:82 | edge.local |
| E09 | ExperienceEvent.Builder | `public Builder setData(Map<String, Object>)` | EE:96 | edge.local |
| E10 | ExperienceEvent.Builder | `public Builder setXdmSchema(Schema)` | EE:114 | edge.local |
| E11 | ExperienceEvent.Builder | `public Builder setXdmSchema(Map<String, Object>, String)` | EE:146 | edge.local |
| E12 | ExperienceEvent.Builder | `public Builder setXdmSchema(Map<String, Object>)` | EE:167 | edge.local |
| E13 | ExperienceEvent.Builder | `public ExperienceEvent build()` | EE:185 | edge.local |
| E14 | ExperienceEvent | `public String getDatastreamIdOverride()` | EE:205 | edge.local |
| E15 | ExperienceEvent | `public Map<String, Object> getDatastreamConfigOverride()` | EE:212 | edge.local |
| E16 | ExperienceEvent | `public Map<String, Object> getData()` | EE:219 | edge.local |
| E17 | ExperienceEvent | `public Map<String, Object> getXdmSchema()` | EE:233 | edge.local |
| E18 | EdgeCallback | `void onComplete(List<EdgeEventHandle>)` | ECB:26 | edge.callback |
| E19 | EdgeCallbackWithError | `void onComplete(List<EdgeEventHandle>)` | ECBE:32 | edge.callback |
| E20 | EdgeCallbackWithError | `void onError(List<EdgeEventError>)` | ECBE:39 | edge.callback |
| E21 | EdgeEventHandle | `public String getType()` | EH:53 | edge.local |
| E22 | EdgeEventHandle | `public List<Map<String, Object>> getPayload()` | EH:60 | edge.local |
| E23 | EdgeEventHandle | `public String toString()` | EH:91 | edge.local |
| E24 | EdgeEventError | `public String getType()` | ER:33 | edge.local |
| E25 | EdgeEventError | `public int getStatus()` | ER:38 | edge.local |
| E26 | EdgeEventError | `public String getTitle()` | ER:43 | edge.local |
| E27 | EdgeEventError | `public String getDetail()` | ER:48 | edge.local |
| E28 | EdgeEventError | `public String toString()` | ER:53 | edge.local |
| E29 | xdm.Schema | `String getSchemaVersion()` | XS:24 | edge.local |
| E30 | xdm.Schema | `String getSchemaIdentifier()` | XS:31 | edge.local |
| E31 | xdm.Schema | `String getDatasetIdentifier()` | XS:37 | edge.local |
| E32 | xdm.Schema | `Map<String, Object> serializeToXdm()` | XS:43 | edge.local |
| E33 | xdm.Property | `Map<String, Object> serializeToXdm()` | XP:21 | edge.local |
| E34 | xdm.Formatters | `public static List<Map<String, Object>> serializeFromList(List<? extends Property>)` | XF:27 | edge.local |

**Local behavior:** Builders deep-copy supplied data/XDM/config maps where implemented; no dispatch until E02/E03. Missing XDM makes `build()` return null; empty XDM can build but is rejected by sendEvent. A successful build locks the builder; later setters/build throw `UnsupportedOperationException`. `setXdmSchema(Schema)` calls the application's serialization and dataset getter. SDK contracts do not guarantee arbitrary application implementations are side-effect-free. No public ExperienceEvent/handle/error constructor exists other than the listed builder. Handle/error construction and event index are internal. `Formatters` skips null properties and returns an empty list for null input. No deprecated API declaration occurred in the reviewed public bodies.

#### Edge Identity — 25 callables

Classes are in `com.adobe.marketing.mobile.edge.identity`.

| ID | Class | Signature | Source | Profiles |
|---|---|---|---|---|
| I01 | Identity | `public static String extensionVersion()` | I:47 | identity.local |
| I02 | Identity | `public static void getExperienceCloudId(AdobeCallback<String>)` | I:58 | identity.ecid |
| I03 | Identity | `public static void getUrlVariables(AdobeCallback<String>)` | I:141 | identity.url |
| I04 | Identity | `public static void updateIdentities(IdentityMap)` | I:213 | identity.update |
| I05 | Identity | `public static void updateOperationalData(Map<String, Object>)` | I:249 | identity.operational; identity.operational.rules; identity.operational.results |
| I06 | Identity | `public static void removeIdentity(IdentityItem, String)` | I:285 | identity.remove |
| I07 | Identity | `public static void getIdentities(AdobeCallback<IdentityMap>)` | I:316 | identity.get |
| I08 | IdentityMap | `public IdentityMap()` (implicit default constructor) | IM:33 | identity.local |
| I09 | IdentityMap | `public List<IdentityItem> getIdentityItemsForNamespace(String)` | IM:46 | identity.local |
| I10 | IdentityMap | `public List<String> getNamespaces()` | IM:71 | identity.local |
| I11 | IdentityMap | `public void addItem(IdentityItem, String)` | IM:83 | identity.local |
| I12 | IdentityMap | `public void removeItem(IdentityItem, String)` | IM:93 | identity.local |
| I13 | IdentityMap | `public boolean isEmpty()` | IM:120 | identity.local |
| I14 | IdentityMap | `public String toString()` | IM:125 | identity.local |
| I15 | IdentityItem | `public IdentityItem(String, AuthenticatedState, boolean)` | II:48 | identity.local |
| I16 | IdentityItem | `public IdentityItem(String)` | II:72 | identity.local |
| I17 | IdentityItem | `public IdentityItem(IdentityItem)` | II:81 | identity.local |
| I18 | IdentityItem | `public String getId()` | II:90 | identity.local |
| I19 | IdentityItem | `public AuthenticatedState getAuthenticatedState()` | II:99 | identity.local |
| I20 | IdentityItem | `public boolean isPrimary()` | II:109 | identity.local |
| I21 | IdentityItem | `public String toString()` | II:114 | identity.local |
| I22 | IdentityItem | `public boolean equals(Object)` | II:126 | identity.local |
| I23 | IdentityItem | `public int hashCode()` | II:140 | identity.local |
| I24 | AuthenticatedState | `public String getName()` | AS:39 | identity.local |
| I25 | AuthenticatedState | `public static AuthenticatedState fromString(String)` | AS:43 | identity.local |

**Local behavior:** Map mutation changes the application model, not stored SDK identities until I04/I06. Empty IDs/namespaces are rejected by map insertion; null IdentityItem ID throws `IllegalArgumentException`. The short constructor defaults to ambiguous/false; missing authentication state defaults ambiguous. `fromString` recognizes authenticated/loggedOut case-insensitively and defaults ambiguous. Enum constants are fields. Identity accessors/toString can contain private identity material; no such methods were executed to generate examples. `MobileCore.updateProfileAttributes` is **Core-owned**, not a missing Identity overload.

#### Consent — 3 callables

| ID | Class | Signature | Source | Profiles |
|---|---|---|---|---|
| C01 | Consent | `public static String extensionVersion()` | C:42 | consent.local |
| C02 | Consent | `public static void update(Map<String, Object>)` | C:54 | consent.update; consent.server; edge.responses |
| C03 | Consent | `public static void getConsents(AdobeCallback<Map<String, Object>>)` | C:82 | consent.get |

There is **no public Consent entity or builder in the reviewed source**: `code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/Consents.java:21` declares package-private `final class Consents`. Its constructors, timestamp accessors, merge and XDM serialization are internal, even though its `equals(Object)` override is public. Customer input/output is a `Map<String,Object>`. That visibility exclusion is intentional, not omitted getter coverage.

#### Edge Bridge — 1 callable

| ID | Class | Signature | Source | Profiles |
|---|---|---|---|---|
| B01 | EdgeBridge | `public static String extensionVersion()` | B:42 | bridge.local |

`EXTENSION` is a registration field. `MobileCore.trackAction(String, Map)` and `MobileCore.trackState(String, Map)` are Core APIs, not EdgeBridge methods. Their received events can trigger `bridge.forward` below. No Bridge model/builder/callback facade is declared.

### Event chains, conditions and payload templates

All JSON is **normalized illustrative structure**, not literal dumps or uploads. Angle-bracket values are safe type/value placeholders; they are not real IDs, tokens, URLs or user/configuration data. Optional fields shown together are not promised to coexist. Retain fixed flags literally. The companion JSON includes every profile's source references, eligibility and conditions.

#### Envelope/link semantics

Dependency definitions: `aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/Event.java:83-94,195-255`; full event constants: `code/core/src/main/java/com/adobe/marketing/mobile/EventType.java:32-51` and `code/core/src/main/java/com/adobe/marketing/mobile/EventSource.java:28-48`.

* Each builder creates a new UUID. Root request envelopes have `parentId: null`, `responseId: null`.
* `chainToParentEvent` sets parentId only; `inResponseToEvent` sets parentId **and** responseId to the request UUID.
* Edge handle/error builders use `setParentId`: responseId stays null. `eventData.requestId` is the network request ID, **not** the facade event UUID. `requestEventId` appears only when attributable.
* Timestamps are event creation timestamps; Bridge `xdm.timestamp` explicitly uses the parent event timestamp.
* Passing an event to shared-state creation versions the shared state; it does not implicitly link separately built notification/forwarding envelopes.

**Example redaction is not exporter behavior:** real identity values and illustrative IDs are redacted only in these documentation examples. The existing telemetry exporter intentionally preserves the original **eventId / parentId / responseId / timestamp**. Nothing in this document requires dropping, regenerating or redacting those exported correlation fields.

#### Edge: `edge.request` → `edge.responses` / callback

```text
E02/E03
 → validate event/XDM → ExperienceEvent.toObjectMap → "AEP Request Event"
 → EdgeExtension registered EDGE/requestContent listener
 → configuration + Identity readiness / ignore checks → persistent hit queue
 → EdgeHitProcessor request builder / Edge Network
 → NetworkResponseHandler → attributed handles, errors or warnings
 → callback unregistration: onComplete(handles), then optional onError(errors)
```

Executable route/readiness: `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeExtension.java:177-264`.
Serialization: `code/edge/src/main/java/com/adobe/marketing/mobile/ExperienceEvent.java:246-272`.
Request/network callback: `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeHitProcessor.java:290-365`.
Names/keys: `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeConstants.java:22-30,70-91`.

```json
{
  "eventName": "AEP Request Event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {"<caller-xdm-key>": "<safe-value>"},
    "data": {"<caller-data-key>": "<safe-value>"},
    "datasetId": "<dataset-id>",
    "config": {
      "datastreamIdOverride": "<datastream-id>",
      "datastreamConfigOverride": {"<service-key>": "<safe-override>"}
    }
  }
}
```

Only XDM is required by the facade; optional nonempty data/dataset/config members are included by serialization. Null event, empty XDM or empty serialized data returns without dispatch/registered callback. E03 delegates to E02; identical payloads cannot identify overload. Neither the Java return nor the optional POC telemetry marker `"dispatched"` establishes network success.

```json
[
  {
    "eventName": "AEP Response Event Handle",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "<server-handle-type; fallback com.adobe.eventSource.responseContent>",
    "eventData": {
      "type": "<server-handle-type>",
      "payload": [{"<server-key>": "<redacted-value>"}],
      "requestId": "<network-request-id>",
      "requestEventId": "<attributed-event-id>"
    }
  },
  {
    "eventName": "AEP Error Response",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.errorResponseContent",
    "eventData": {
      "type": "<error-type>",
      "status": "<integer-status>",
      "title": "<redacted-title>",
      "detail": "<redacted-detail>",
      "requestId": "<network-request-id>",
      "requestEventId": "<attributed-event-id>"
    }
  },
  {
    "eventName": "AEP Response Event Handle",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.responseContent",
    "eventData": {
      "type": "<warning-type>",
      "requestId": "<network-request-id>",
      "requestEventId": "<attributed-event-id>"
    }
  }
]
```

The producer copies server error/warning objects; their keys are not a fixed SDK-required schema. A server `eventIndex` can also be present. Handle type determines **eventSource dynamically**; eventName is fixed. Do not expand an unnamespaced handle source such as `consent:preferences` into a guessed `com.adobe...` string.

`code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:588-710,754-781,848-898` proves dispatch and attribution: indexed responses map to their request; one waiting event permits no-index attribution; global `state:store` / `locationHint:result` can broadcast with null parent in a batch. Other no-index batch handles have no per-event attribution. Omit requestEventId when absent. State/location handles can update persistence, subject to reset handling.

`code/edge/src/main/java/com/adobe/marketing/mobile/CompletionCallbacksManager.java:90-149` calls onComplete with possibly empty handles and then onError only for an extended callback and accumulated errors; callback exceptions are caught/logged. **onComplete, error arrival, and response-stream completion are not whole API success.** An API dropped before network completion need not have a guaranteed callback outcome.

##### Supported completion event — `edge.complete`, not default facade output

```json
{
  "eventName": "AEP Response Complete",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.contentComplete",
  "eventData": {"requestId": "<network-request-id>"}
}
```

`code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:310-327,388-396` explicitly requires top-level `eventData.request.sendCompletion=true`, default false. The reviewed public sendEvent serializer, Consent and Bridge producers do **not** add it. This event uses inResponseToEvent but is not promised for E02/E03, C02 or Bridge forwarding. Callback unregistration is a separate action.

#### Edge location — local state with EventHub requests

```text
E04 → Edge Request Location Hint → handleGetLocationHint
    → Edge Location Hint Response → callback(hint or null)
E05 → Edge Update Location Hint → handleSetLocationHint → local state; no paired response
```

```json
[
  {"eventName":"Edge Request Location Hint","eventType":"com.adobe.eventType.edge","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"locationHint":true}},
  {"eventName":"Edge Location Hint Response","eventType":"com.adobe.eventType.edge","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"locationHint":"<hint-or-null>"}},
  {"eventName":"Edge Update Location Hint","eventType":"com.adobe.eventType.edge","eventSource":"com.adobe.eventSource.updateIdentity","eventData":{"locationHint":"<hint-or-null>"}}
]
```

`edge.location.get`: E:120-192; response in `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeExtension.java:330-346`. Null callback means no request. Timeout is 5000ms; absent/expired hint returns null normally. Null/malformed response and type errors use AdobeCallbackWithError only. Request is root; response uses inResponseToEvent.

`edge.location.set`: E:207-222 and `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeExtension.java:354-368`. Null/empty clears the hint; handler uses 1800-second TTL (`code/edge/src/main/java/com/adobe/marketing/mobile/EdgeConstants.java:41`). Root update event, no dedicated response. This local routing preference is not a network request.

#### Identity reads and local mutation

```text
I02 / I07 → EDGE_IDENTITY/requestIdentity
 → registered handleRequestIdentity → handleGetIdentifiersRequest
 → "Edge Identity Response Content One Time" → select ECID / return IdentityMap
I03 → URL-variable request → resolved configuration + local ECID/org
 → URL-variable response → callback or error
I04 / I06 → updateIdentity / removeIdentity
 → pending XDM state → parse → state mutation/persistence → resolve XDM state
```

Selectors: `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityExtension.java:128-139,164-190`.
Read response: same file `:355-366`; URL producer `:198-281`; mutation handlers `:289-347`.
Names and serialization keys: `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityConstants.java:28-55,115-121`;
`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityMap.java:258-276`;
`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityItem.java:153-167`.

```json
[
  {"eventName":"Edge Identity Request ECID","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"eventName":"Edge Identity Request Identities","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"eventName":"Edge Identity Response Content One Time","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"identityMap":{"<namespace>":[{"id":"<redacted-identity>","authenticatedState":"<ambiguous|authenticated|loggedOut>","primary":"<boolean>"}]}}},
  {"eventName":"Edge Identity Request URL Variables","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"urlvariables":true}},
  {"eventName":"Edge Identity Response URL Variables","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"urlvariables":"<redacted-encoded-identifiers-or-null>"}},
  {"eventName":"Edge Identity Update Identities","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.updateIdentity","eventData":{"identityMap":{"<namespace>":[{"id":"<redacted-identity>","authenticatedState":"<ambiguous|authenticated|loggedOut>","primary":"<boolean>"}]}}},
  {"eventName":"Edge Identity Remove Identities","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.removeIdentity","eventData":{"identityMap":{"<namespace>":[{"id":"<redacted-identity>","authenticatedState":"<ambiguous|authenticated|loggedOut>","primary":"<boolean>"}]}}}
]
```

* All getter requests are roots, corresponding responses use inResponseToEvent; no network round trip is required. Callback timeouts are **500ms**, not Edge/Consent's 5000ms.
* Null callback rejects dispatch. ECID getter selects the first ECID from the shared map response, returning empty string when absent. Identity-map getter can return an empty map. URL generation requires configuration/org and ECID; missing prerequisites still yield a response containing null and the facade's unexpected-error path.
* Errors are returned only to an `AdobeCallbackWithError`; plain callback has no error channel. Core callback/error contracts are dependencies, not newly counted Edge-family definitions.
* updateIdentities rejects a null/empty map, then performs additive local merge. removeIdentity rejects null item/empty namespace. A nonnull item with empty ID can yield an empty map after map-level validation **yet still dispatch**. Removal changes client state, not the server User Profile Graph. No dedicated update/remove response producer is claimed.
* Both mutation helpers filter reserved ECID/IDFA/GAID namespaces before changing stored identities (`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityProperties.java:208-225,256-269`). `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityState.java:184-196` calls persistence after mutation. `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityStorageManager.java:98-120` writes serialized XDM to `identity.properties` when the named collection exists; unavailable storage logs/returns, so event publication is not proof of durable persistence. No further explicit customer-mutation event is built in these helpers.
* Identity values, URL variable strings and map toString output must never be copied into an exported catalog. Profiles show structure only.

#### Operational data — explicit conditional rule flow and snapshot mismatch

```text
I05 → "Update Operational Data"
 → Identity listener → "Operational data for rules engine"
 → ONLY a matching configured forward-operational-data rule:
      "Forward operational data" (configured type/source)
 → ONLY Identity's exact edgeIdentity/responseOperationalData selector:
      queued rule response → dedup/app checks
      → unchanged/missing app/internal error: Sync Result, no device request
      → changed: "Edge device attributes request" (edgeDeviceAttributes)
          -X- assigned Edge listener expects edgeBypassConsent
```

Executable producer/branches:
`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/OperationalDataSync.java:85-105,109-234,238-334`.
Selectors:
`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityExtension.java:141-159`.
Exact names/strings:
`code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityConstants.java:40-44,181-205`.

```json
[
  {"eventName":"Update Operational Data","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.requestOperationalData","eventData":{"<attribute>":"<safe-json-value>"}},
  {"eventName":"Operational data for rules engine","eventType":"com.adobe.eventType.generic.operationalData","eventSource":"com.adobe.eventSource.requestContent","eventData":{"<attribute>":"<safe-json-value>"}},
  {"eventName":"Forward operational data","eventType":"<configured-consequence-eventType>","eventSource":"<configured-consequence-eventSource>","eventData":{"<enabled-non-null-path>":"<safe-value>"}},
  {"eventName":"Edge device attributes request","eventType":"com.adobe.eventType.edgeDeviceAttributes","eventSource":"com.adobe.eventSource.requestContent","eventData":{"timezone":"<safe-timezone>","tokens":{"<token-kind>":"<redacted-token>"},"<other-allowed-attribute>":"<safe-value>","app":{"id":"<application-package>","platform":"fcm"}}},
  {"eventName":"Operational Data Sync Result","eventType":"com.adobe.eventType.edgeIdentity","eventSource":"com.adobe.eventSource.responseContent","eventData":{"status":"<success|failed>","reason":"<empty|unchanged|missingAppIdentifier|internalError|response-reason>","attributes":{"<attribute>":"<safe-value>"}}},
  {"eventName":"Operational data confirmed","eventType":"com.adobe.eventType.messaging","eventSource":"com.adobe.eventSource.responseContent","eventData":{"tokens":{"<token-kind>":"<redacted-token>"}}}
]
```

The rules producer is verified in dependency `aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/launch/rulesengine/LaunchRulesConsequence.kt:50-61,408-453`: name is fixed, **type/source are genuinely configuration-defined**, only selected nonnull paths forwarded. No enabled deployment rule is asserted. An empty selection can dispatch an empty rule event, which Identity ignores. This is `identity.operational.rules` (**DYNAMIC routing**, not a guessed fixed event).

I05 rejects null/empty input and catches copy exceptions. Forwarding to rules chains to the API event. A matching rule response enters FIFO processing with one active request. `buildEdgeRequest` reads `edgeidentity.optimizeOperationalProfileDataSync` with executable default **true** at `OperationalDataSync.java:176-183`. String timezone/tokens may be deduplicated against confirmed values; owner comparison is consent-dependent. Other attributes are retained. The API does not validate all values. Caller `app` is removed; SDK package and literal `"fcm"` replace it.

An unchanged result is `"success"`/`"unchanged"` **without a network send**. Missing application identifier reports failed/missingAppIdentifier. Matching response success commits dedup state, can emit token confirmation and reports success with empty reason; failure keeps values unsynced. These later branches require an actual matching response, not mere stream completion. All derived events chain to their immediate parent (rule response or device response), not necessarily the original API root; responseId is not set by chaining.

##### Verified cross-repository mismatch — do not fabricate completion

Assigned Edge **accepts and responds using** `com.adobe.eventType.edgeBypassConsent`, while assigned Identity **publishes and listens using** `com.adobe.eventType.edgeDeviceAttributes`. Evidence:

* `code/edge/src/main/java/com/adobe/marketing/mobile/EdgeExtension.java:203-209`
* `code/edge/src/main/java/com/adobe/marketing/mobile/DeviceAttributesConstants.java:28-31`
* `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityConstants.java:183-186`
* `code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityExtension.java:154-159`

The Edge internal response contract is `edge.device.response`, **not a proven connected response to I05**:

```json
[
  {"eventName":"Device Attributes Response","eventType":"com.adobe.eventType.edgeBypassConsent","eventSource":"com.adobe.eventSource.responseContent","eventData":{"status":"<success|failed|dropped>","reason":"<optional-reason>","httpStatus":"<optional-integer>"}},
  {"eventName":"Device Attributes Response","eventType":"com.adobe.eventType.edgeBypassConsent","eventSource":"com.adobe.eventSource.responseContent","eventData":{"status":"dropped","reason":"invalidPayload","requestEventId":"<undecodable-stored-hit-id>"}}
]
```

`code/edge/src/main/java/com/adobe/marketing/mobile/DeviceAttributesEvents.java:50-96` creates these: normal response chains to request; undecodable stored hit has no parent and uses requestEventId instead. `EdgeExtension.java:480-586` has explicit queue/config/payload failure results. No adapter was established; no build/runtime success claim bridges this mismatch.

#### Consent: `consent.update`, `consent.get`, `consent.server`

```text
C02 → "Consent Update Request"
 → validate nonempty consents → timestamp new fragments → merge/persist
 → changed OR outside 1000ms suppression window:
      → XDM shared state + "Consent Preferences Updated" (current/merged)
      → "Edge Consent Update Request" (new fragments only)
          → Edge consent queue/network → optional handles/errors/warnings
          → optional consent:preferences handle → merge/persist + notify only
C03 → "Get Consents Request" → current consents
    → "Get Consents Response" → callback
```

Public producers: C:54-118. Executable listeners and suppression:
`code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentExtension.java:125-151,163-198`.
Notification/Edge forwarding/response builders: same file `:279-289,345-386`.
Names: `code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentConstants.java:47-53`.
Timestamp and serialization: `code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/Consents.java:92-112,195-200`.
Persistence: `code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentManager.java:156-173`.

```json
[
  {"eventName":"Consent Update Request","eventType":"com.adobe.eventType.edgeConsent","eventSource":"com.adobe.eventSource.updateConsent","eventData":{"consents":{"collect":{"val":"<y|n|p>"},"<other-consent>":{"<key>":"<safe-value>"}}}},
  {"eventName":"Consent Preferences Updated","eventType":"com.adobe.eventType.edgeConsent","eventSource":"com.adobe.eventSource.responseContent","eventData":{"consents":{"collect":{"val":"<stored-or-default-value>"},"metadata":{"time":"<ISO8601-trigger-time>"}}}},
  {"eventName":"Edge Consent Update Request","eventType":"com.adobe.eventType.edge","eventSource":"com.adobe.eventSource.updateConsent","eventData":{"consents":{"collect":{"val":"<new-value>"},"metadata":{"time":"<ISO8601-trigger-time>"}}}},
  {"eventName":"Get Consents Request","eventType":"com.adobe.eventType.edgeConsent","eventSource":"com.adobe.eventSource.requestContent","eventData":null},
  {"eventName":"Get Consents Response","eventType":"com.adobe.eventType.edgeConsent","eventSource":"com.adobe.eventSource.responseContent","eventData":{"consents":{"<consent-key>":{"<key>":"<safe-value>"}}}},
  {"eventName":"AEP Response Event Handle","eventType":"com.adobe.eventType.edge","eventSource":"consent:preferences","eventData":{"type":"consent:preferences","payload":[{"collect":{"val":"<server-value>"},"metadata":{"time":"<optional-server-time>"}}],"requestId":"<network-request-id>","requestEventId":"<optional-attributed-event-id>"}}
]
```

* The facade accepts a nonempty top-level map unchanged. The handler requires nonempty nested `consents`; other JSON-compatible consent fragments are allowed. The example is not an exhaustive consent-schema validator.
* Timestamp is generated from triggering event time. Duplicate unchanged preferences within the window are dropped; the outside-window comparison is strictly `event.timestamp > lastUpdate + 1000`.
* Preferences notification carries current merged consent/defaults; Edge forwarding carries newly updated fragments. Both builders are new roots, with **no response/parent linkage**, despite shared-state versioning. The getter response alone uses inResponseToEvent.
* C03 rejects null callback; 5000ms response timeout. Current empty state returns `{"consents":{}}`; null/missing response data invokes unexpected error only for error-capable callbacks.
* Optional server `consent:preferences` response uses the exact unnamespaced source. Handler reads payload[0], ignores empty/invalid input and unchanged absent/equal-timestamp preferences, otherwise stamps response-event time and merges/persists/notifies. It **does not** forward a new Edge request, avoiding a response loop (`ConsentExtension.java:210-267`). These handles may arise from other Edge traffic, not only C02.
* Consent initialization/default configuration can independently produce preferences notifications (`ConsentExtension.java:145-151,297-332`). Do not attribute every such event to a customer update.

#### Edge Bridge: `bridge.forward` is listener-driven, not B01

```text
Core tracking event generic.track/requestContent
  OR rulesEngine/responseContent with valid Analytics consequence
 → EdgeBridge listener → clone + format → "Edge Bridge Request"
 → Edge request listener → queue/network → optional edge.responses
```

Executable registration:
`code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeExtension.java:58-65`.
Generic/rules entry gates: same file `:72-154`; actual producer `:167-197`; formatter `:245-405`.
Constants:
`code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeConstants.java:25-65`.

```json
{
  "eventName":"Edge Bridge Request",
  "eventType":"com.adobe.eventType.edge",
  "eventSource":"com.adobe.eventSource.requestContent",
  "eventData":{
    "xdm":{"eventType":"analytics.track","timestamp":"<ISO8601-parent-time>"},
    "data":{
      "__adobe":{
        "analytics":{
          "linkName":"<action>",
          "linkType":"other",
          "pageName":"<state>",
          "cp":"<foreground|background>",
          "contextData":{"<context-key>":"<safe-string>","a.AppID":"<application-name-version>"}
        }
      },
      "<additional-input-key>":"<safe-value>"
    }
  }
}
```

Action maps to linkName and literal linkType `"other"`; state maps to pageName; both may occur in crafted input, not necessarily every normal call. Context data permits string values only: `&&`-prefixed keys lose that prefix and become analytics fields, other keys enter contextData. Invalid/empty keys are discarded. Additional fields pass through. Analytics enrichment only runs when analytics fields exist; cp reflects foreground/background and defaults foreground; AppID is added only when available (`code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeProperties.java:35-70`).

Empty data, clone failure or no mappable fields prevents dispatch. The rules path additionally requires `triggeredconsequence.type == "an"`, a nonempty consequence ID and detail. Rule forwarding is not unconditional. Bridge chains to the triggering Core/rule event, and xdm.timestamp uses that parent's timestamp. It supplies **no Bridge-owned callback/response**. B01 only reads a version constant, not this event.

### Catalog eligibility and naming

* EventHub-publishing facades can support **request-dispatched** observations. Multiple overloads can share an indistinguishable payload; no-event model/metadata calls cannot be counted from EventHub roots.
* Getter responses establish local-state callback data, not server ingestion. Errors, warnings, callbacks and stream completion must remain separate observations.
* Every reviewed SDK producer has a fixed eventName. There is **no genuinely caller-defined eventName** in these four facade flows. Dynamic XDM keys, action/state values, server handle type/source and configured rules type/source must not be relabeled as dynamic event names.
* Optional telemetry integration adapters are infrastructure: a reflected telemetry operation-completed `"dispatched"` marker is not whole-API success. Plugin event producers/payloads are outside the four extensions' customer surface.

### Coverage, exclusions and true gaps

| Criterion | Status | Strongest exact evidence | Confidence | Gap |
|---|---|---|---|---|
| Explicit customer signatures | 63 inventoried / 63 documented; complete source reconciliation | 15 customer files; 62 explicit declarations + implicit IdentityMap constructor, with per-file accounting above | High | No unaccounted customer file/signature |
| Direct and reviewed downstream payloads | 23 profiles, including NO_EVENT/CALLBACK_ONLY/DYNAMIC | Exact producer and constant ranges in profile sourceRefs | High | No runtime delivery guarantee |
| Operational cross-repository completion | Mismatch documented | IdentityConstants.java:183-186 versus Edge DeviceAttributesConstants.java:28 and registration EdgeExtension.java:203-209 | High | No functioning edgeDeviceAttributes↔edgeBypassConsent adapter established |
| Deployment operational forwarding | Conditional only | Core LaunchRulesConsequence.kt:408-453; IdentityExtension.java:141-159 | High | No customer rule configuration observed; no enabled-rule claim |
| Source access / snapshot | All four Scout-backed; tracked manifests verified | Recorded HEADs and source-state IDs | High | Derived graph views not independently verified |
| Complete candidate-file census | 82 production files accounted | Read-only Git filename census + Scout type/member anchors: 15 customer / 62 nonpublic or internal / 5 explicit public infrastructure files | High | None within the customer-surface policy |
| Independent ABI certification | Not performed; distinct from source/payload coverage | Declaration census is complete without relying on an ABI baseline | Not assessed | Release/ABI compatibility certification only |

**Exclusions:** Extension subclasses and extension-author/platform infrastructure; package-private queue/network/state/persistence/request builders; anonymous SDK callbacks; package-private `Consents`, `ECID`, `TimeZoneAttributeHandler`, `RequestMetadata`/Builder; public members enclosed in nonpublic implementations; explicitly accounted public SDKConfig/Datastream metadata implementation and optional POC TelemetryBridge adapters; registration/enum fields; compiler synthetic/generated enum and data-class methods; inherited Object methods not explicitly overridden; test/sample/tutorial/generated app models; unrelated rule consequences. Core-owned tracking/profile-attribute APIs are dependencies rather than duplicate extension facade rows. Core AdobeCallback/AdobeCallbackWithError are referenced contracts, not re-inventoried definitions.

Full dirty-snapshot summaries, source-state IDs, gaps and 63 unique inventory rows are retained in `edge-family.json`. No SDK production source, builds, Git state, wiki, runtime catalogs or user final Markdown was edited by artifact authoring. No build, agent, workflow, commit, push or runtime identity export was performed.

---

<a id="family-classic-family"></a>

## Classic Android extension API → EventHub contracts

**Summary:** Five checked-out repositories expose 77 inventoried callable declarations, mapped to 45 profiles, including local-only models, builders and callback contracts.

```yaml
request_summary: Exhaustive customer API and EventHub payload inventory for classic-family
doc_type: api
target: Analytics, Audience, Campaign, CampaignClassic, Target
output_mode: local
output_path: classic-family.md + classic-family.json
strategy:
  reader_journey: Find an exact overload, follow its profile, then check dispatch/rejection and downstream conditions.
  outline_headings: [Evidence and scope, API inventory, Flows, Payload templates, Conditions, Coverage]
sources:
  repo_files: [facade declarations, public models, Event.Builder producers, referenced constants]
  commands: [git rev-parse HEAD, git branch --show-current, git status --porcelain, git ls-files]
observed:
  - Four repositories supplied Scout production evidence.
  - Target production source was outside the indexed file set; native evidence is UNINDEXED.
  - No checked-in API baseline candidates or tracked AGENTS.md were found.
unknown:
  - Target index-specific affected-test/impact evidence is unavailable.
recommendations: []
```

### Evidence, scope and interpretation

This is a **source-snapshot contract**, not a list of historical APIs, a runtime dump, an `sdk.usage` upload, or proof that an extension is registered in a particular application. `classic-family.json` is the machine-readable companion: every row has its exact source path/line, profile references and method-specific conditions; every profile has producer/constant references.

The requested fixed structure is used without an interactive documentation checkpoint. No SDK code/config edits, builds, installs, commits, pushes, wiki writes or parent-final-document edits were performed. The required Scout attach operation created untracked `.scout/` metadata.

#### Snapshots

All five repositories were reviewed on branch `poc/telemetry-integration`. `git status --porcelain` showed no tracked source modifications; `.scout/` was untracked. No checked-in `.api`, API-baseline JSON/text, or `api/` baseline candidates appeared in their tracked manifests.

| Repository | HEAD | API declarations | Evidence |
|---|---|---:|---|
| `aepsdk-analytics-android` | `ac2798cf7dc88ba86740f2702c0a605419fe902e` | 7 | Scout |
| `aepsdk-audience-android` | `4a98fba70984f4ba281c60a1c4a2541ffe1028b4` | 4 | Scout |
| `aepsdk-campaign-android` | `30aaf4b93dae675608f8e34d9a6ed6d48bf420f3` | 6 | Scout |
| `aepsdk-campaignclassic-android` | `f9cdf6250dc00c36c21fd1e61c8af0ad64ae654e` | 5 | Scout |
| `aepsdk-target-android` | `7c0ca4781edb70635d67baece42225d0f8c0895a` | 55 | **UNINDEXED** native source |

Scout Target recovery was bounded: one attach attempt, search/keyword retry, resumed investigation bundle `target-public-customer-2b0dc712ce53c1cad29f5165`, then targeted `code/target` regex closure. The latter explicitly reported **zero candidate files/outside indexed file set**. Documentation-only search results were not treated as source completeness. Read-only native Target source inspection supplied the declarations and producers below. This does **not** establish index-specific test/impact coverage. Other Scout responses reported unverified freshness; current exact source declarations were subsequently checked against the inventory.

#### Normalized payload and correlation conventions

Every event template uses the exact full `eventName`, `eventType`, `eventSource` and a source-backed `eventData` shape. Angle brackets mean safe placeholders, never literal SDK constants or real customer values. Optional keys shown in an example may be absent; zero is an illustrative numeric value. A `null` eventData means no `setEventData` call in that producer.

* **U — unpaired:** new event UUID, `parentId: null`, `responseId: null`, build-time timestamp.
* **P — paired:** new response UUID; `parentId` and `responseId` both equal the triggering request UUID. The timestamp is the response's build time, not the request timestamp.
* Target load replies use **U** plus `eventData.responseEventId` and `responsePairId`; do not turn those into Event-level response/parent IDs.
* `inResponseToEvent` sets both links: `aepsdk-core-android/code/core/src/main/java/com/adobe/marketing/mobile/Event.java:186-227`. UUID/default null links: the same file, `:68-94`.
* Full type/source literals: `aepsdk-core-android/code/core/src/main/java/com/adobe/marketing/mobile/EventType.java:26-55` and `code/core/src/main/java/com/adobe/marketing/mobile/EventSource.java:26-39`.
* All chains below are conditional on the relevant extension having been registered and dependencies being available. Shared state/persistence/network work is not itself invented as an EventHub response.

**Catalog eligibility:** Direct request events (and raw APIs' fixed envelopes) can evidence a dispatched API occurrence. They do not prove validation by the extension, HTTP success, message delivery or business success. Downstream events must retain their conditions and publisher/API-owner distinction; they must not inflate API-call counts. `NO_EVENT` and `CALLBACK_ONLY` APIs cannot be exhaustively observed with EventHub listeners alone.

**POC instrumentation:** Optional reflection-based telemetry bridges are implementation infrastructure. `operationStarted`/`operationCompleted` are not family `Event.Builder` producers. A rejected family call may still invoke that optional bridge. External Telemetry implementation and upload serialization are outside these five repositories' API contracts.

### Analytics

Declaration file: `aepsdk-analytics-android/code/analytics/src/phone/java/com/adobe/marketing/mobile/Analytics.java`. All entries are static facade methods; version is `3.0.2`. No separate public Analytics customer models/callback interfaces are declared. Core `AdobeCallback` and `AdobeCallbackWithError` are external contracts, not extra overloads.

#### Complete API inventory

| ID | Signature | Line | Profiles |
|---|---|---:|---|
| AN01 | `String extensionVersion()` | 44 | NO_EVENT |
| AN02 | `void getTrackingIdentifier(AdobeCallback<String> callback)` | 57 | AN.getTracking, AN.identityResponse |
| AN03 | `void getQueueSize(AdobeCallback<Long> callback)` | 109 | AN.getQueue, AN.queueResponse |
| AN04 | `void clearQueue()` | 153 | AN.clear |
| AN05 | `void sendQueuedHits()` | 165 | AN.send, AN.hitResponse |
| AN06 | `void getVisitorIdentifier(AdobeCallback<String> callback)` | 186 | AN.getVisitor, AN.identityResponse |
| AN07 | `void setVisitorIdentifier(String visitorID)` | 231 | AN.setVisitor, AN.identityResponse |

#### Flows and conditions

```text
AN02 / AN06 → MobileCore dispatchWithResponseCallback
  → Analytics requestIdentity handler → TrackingIdentifierValue [P]
                                      → TrackingIdentifierValue [U]
  → callback extracts aid / vid respectively
AN07 → UpdateVisitorIdentifier [U] → accepted VID update + shared state
  → same paired + unpaired identity producer
AN03 → GetQueueSize [U] → database size → QueueSizeValue [P] → callback
AN04 → ClearHitsQueue [U] → analyticsDatabase.reset() → no dedicated response
AN05 → ForceKickHits [U] → analyticsDatabase.kick(true)
  → zero/many existing HTTP hits → conditional AnalyticsResponse [U]
```

* Null callback in AN02/AN03/AN06 returns **before dispatch**. Response timeout is 5000ms; `fail` is forwarded only when the callback also implements `AdobeCallbackWithError`. Queue callback falls back to `0L` for absent data; ID callbacks can return null.
* AN07 always dispatches at facade level, including null/empty strings. Its handler rejects opted-out privacy or a failed string read; only accepted updates produce shared state and identity responses. Do not equate the Javadoc's null-clearing claim with guaranteed handler acceptance.
* `getAnalyticsIds` omits absent `aid`/`vid`. Both getters share the response producer but are not aliases: they have different direct event names and extract different fields.
* AN05's HTTP response refers to each **original queued hit event**, not necessarily `ForceKickHits`. HTTP 200 and a hit timestamp after the last identity reset are required. No connection/recoverable status retries rather than producing this response.
* Reviewed cross-family listener: if AAM forwarding is enabled and Analytics supplies a nonempty response body, Audience processes it and shares state, **without** invoking the `Audience Manager Profile` event producer on that listener path (`aepsdk-audience-android/code/audience/src/main/java/com/adobe/marketing/mobile/audience/AudienceExtension.java:331-362,452-526`). Do not fabricate an additional profile response in this chain.
* There are no `trackAction`/`trackState` facade methods here; Core owns those APIs. Analytics consuming generic-track/internal A4T events does not transfer their API ownership to Analytics.

Evidence: `code/analytics/src/main/java/com/adobe/marketing/mobile/analytics/internal/AnalyticsExtension.kt:471-557,881-932`; `code/analytics/src/main/java/com/adobe/marketing/mobile/analytics/internal/AnalyticsHitProcessor.kt:142-214`; `code/analytics/src/main/java/com/adobe/marketing/mobile/analytics/internal/AnalyticsConstants.kt:120-140`.

#### Payload profiles

Each array item is a template, not an unconditional sequence. `AN.identityResponse` uses its template twice: P then U.

```json
[
  {"profileId":"AN.getTracking","eventName":"GetTrackingIdentifier","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"AN.getVisitor","eventName":"GetVisitorIdentifier","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"AN.setVisitor","eventName":"UpdateVisitorIdentifier","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"vid":"<visitor-id-or-null>"}},
  {"profileId":"AN.getQueue","eventName":"GetQueueSize","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestContent","eventData":{"getqueuesize":true}},
  {"profileId":"AN.clear","eventName":"ClearHitsQueue","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestContent","eventData":{"clearhitsqueue":true}},
  {"profileId":"AN.send","eventName":"ForceKickHits","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestContent","eventData":{"forcekick":true}},
  {"profileId":"AN.identityResponse","eventName":"TrackingIdentifierValue","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"aid":"<analytics-id>","vid":"<visitor-id>"}},
  {"profileId":"AN.queueResponse","eventName":"QueueSizeValue","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.responseContent","eventData":{"queuesize":0}},
  {"profileId":"AN.hitResponse","eventName":"AnalyticsResponse","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.responseContent","eventData":{"analyticsserverresponse":"<response-body>","headers":{"ETag":"<etag-or-null>","Server":"<server-header-or-null>","Content-Type":"<content-type-or-null>"},"hitHost":"<sanitized-request-url>","hitUrl":"<sanitized-request-payload>","requestEventIdentifier":"<original-hit-event-id>"}}
]
```

### Audience

Declaration file: `aepsdk-audience-android/code/audience/src/phone/java/com/adobe/marketing/mobile/Audience.java`; version `3.0.1`. The complete public-class census found the facade, `AudienceExtension` and the POC telemetry bridge. **No public Audience model, builder or extension-specific customer callback interface exists in this snapshot.** `Map<String,String>` profiles/traits and Core callbacks are not silently omitted model classes.

#### Complete API inventory

| ID | Signature | Line | Profiles |
|---|---|---:|---|
| AU01 | `String extensionVersion()` | 39 | NO_EVENT |
| AU02 | `void getVisitorProfile(AdobeCallback<Map<String, String>> adobeCallback)` | 54 | AU.get, AU.identityResponse |
| AU03 | `void signalWithData(Map<String, String> data, AdobeCallback<Map<String, String>> adobeCallback)` | 117 | AU.signal, AU.profileResponse |
| AU04 | `void reset()` | 179 | AU.reset |

#### Flows and conditions

```text
AU02 → AudienceRequestIdentity [U] → cached state
  → Audience Manager Identities [P] → callback extracts aamprofile
AU03 → AudienceRequestContent [U] → submitSignal → queue → HTTP GET
  → network response handler → Audience Manager Profile [P]
                            → optional nonempty-profile broadcast [U]
AU04 → AudienceRequestReset [U] → clear identifiers + reset timestamp + shared state
```

* AU02 null callback: no dispatch; this getter returns stored profile, not a fresh HTTP request.
* AU03 null callback **does not** prevent dispatch. Null/empty traits are not rejected by the facade. When a callback exists, its EventHub timeout is 5000ms; optional error callback receives dispatch/timeout errors.
* Missing Audience server, opt-out, or a request older than the last reset: a paired **null profile** response, no HTTP for that request.
* Unknown privacy: an early paired null profile is emitted **without returning**; work can still be queued. A callback firing once is not proof the queue is drained.
* Missing queue or an invalid generated URL may stop processing with no eventual response. A network-null condition calls the response handler with null body and retries; nonrecoverable status calls it and discards; recoverable codes retry without that handler invocation.
* Empty network body yields paired empty map. A nonempty parsed profile yields an unpaired broadcast plus paired response. A post-queue identity reset suppresses stale network response publication. Payload arrival alone does not establish a successful trait update.
* AU04 clears UUID/profile and stamps reset time; only the **generic identity** reset branch clears `hitQueue`. Audience.reset has no dedicated completion response and does not itself produce the opt-out-hit event.

Selectors: `code/audience/src/main/java/com/adobe/marketing/mobile/audience/AudienceExtension.java:209-237`. Effects: the same file `:310-322,374-401,614-766,86-129`. Network handling: `code/audience/src/main/java/com/adobe/marketing/mobile/audience/AudienceHitProcessor.java:53-147`. Keys: `code/audience/src/main/java/com/adobe/marketing/mobile/audience/AudienceConstants.java:165-174`.

#### Payload profiles

Request links U; identity reply P; profile reply P and conditionally U. `aamprofile` may be null or empty.

```json
[
  {"profileId":"AU.get","eventName":"AudienceRequestIdentity","eventType":"com.adobe.eventType.audienceManager","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"AU.signal","eventName":"AudienceRequestContent","eventType":"com.adobe.eventType.audienceManager","eventSource":"com.adobe.eventSource.requestContent","eventData":{"aamtraits":{"<trait-key>":"<trait-value>"}}},
  {"profileId":"AU.reset","eventName":"AudienceRequestReset","eventType":"com.adobe.eventType.audienceManager","eventSource":"com.adobe.eventSource.requestReset","eventData":null},
  {"profileId":"AU.identityResponse","eventName":"Audience Manager Identities","eventType":"com.adobe.eventType.audienceManager","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"aamprofile":{"<profile-key>":"<profile-value>"}}},
  {"profileId":"AU.profileResponse","eventName":"Audience Manager Profile","eventType":"com.adobe.eventType.audienceManager","eventSource":"com.adobe.eventSource.responseContent","eventData":{"aamprofile":{"<profile-key>":"<profile-value>"}}}
]
```

### Campaign Standard

Repository: `aepsdk-campaign-android`; version `3.0.3`. Facade declaration file: `code/campaign/src/phone/java/com/adobe/marketing/mobile/Campaign.java`.

#### Complete API inventory

Two public Android receiver callbacks and one public model compatibility override are conservatively included beyond the three facade methods.

| ID | Class | Signature | File and line | Profiles |
|---|---|---|---|---|
| CA01 | Campaign | `String extensionVersion()` | facade:34 | NO_EVENT |
| CA02 | Campaign | `void setLinkageFields(Map<String, String> linkageFields)` | facade:59 | CA.set |
| CA03 | Campaign | `void resetLinkageFields()` | facade:98 | CA.reset |
| CA04 | LocalNotificationHandler | `void onReceive(Context context, Intent intent)` | `code/campaign/src/phone/java/com/adobe/marketing/mobile/LocalNotificationHandler.java:62` | CA.notificationOS (NO_EVENT) |
| CA05 | NotificationDismissalHandler | `void onReceive(Context context, Intent intent)` | `code/campaign/src/phone/java/com/adobe/marketing/mobile/NotificationDismissalHandler.java:35` | CA.dismiss, CA.messageResponse |
| CA06 | campaign.CampaignHit | `String toString()` | `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignHit.java:37` | NO_EVENT |

**Models/callback accessibility reconciliation:** `NotificationSetting` is **package-private** at `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/NotificationSetting.java:16`. Its `build(String,String,long,int,String,Map<String,Object>,String,String)` (`:43`), `getIdentifier` (`:67`), `getContent` (`:74`), `getFireDate` (`:82`), `getDelaySeconds` (`:89`), `getDeeplink` (`:96`), `getUserInfo` (`:103`), `getSound` (`:110`) and `getTitle` (`:117`) are not callable public customer-model methods because the containing class is inaccessible.

Likewise `CampaignMessage` (`code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignMessage.java:30`), `FullScreenMessage` (`code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/FullScreenMessage.java:53`), `AlertMessage` (`code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/AlertMessage.java:37`), `LocalNotificationMessage` (`code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/LocalNotificationMessage.java:27`) and `LocalNotificationService` (`code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/LocalNotificationService.java:26`) are package-private. Their listener overrides and `showMessage` methods are not missing public customer behavior APIs. No public Campaign customer callback interface is declared. `CampaignHit` is public but has a package-private constructor; its sole public override serializes queue data and dispatches no event.

#### Flows and conditions

```text
CA02 → setLinkageFields Event [U] → linkage map JSON/base64 in memory
  → canDownloadRulesWithCurrentState guard → clear rule cache → rules HTTP download
CA03 → resetLinkageFields Event [U] → clear linkage/rules/cache
  → triggerRulesDownload → downloader
CA04 (Android) → validate extras/sender/message → NotificationManager.notify
  → later, separately invoked OS dismissal → CA05
CA05 → MobileCore.collectMessageInfo → Collect Data [U, generic.data/OS]
  → Campaign processMessageInformation (guarded)
  → DataForMessageRequest [U, Campaign/responseContent] → queued tracking HTTP
```

* CA02 rejects null/empty maps **before its family event**. The accepted map is stored as base64 JSON. The set branch checks opt-in, ECID, Campaign server, mcias and property ID before downloading.
* **Executable reset differs from its Javadoc:** CA03's handler calls `triggerRulesDownload()` directly after clearing fields/rules/cache. Neither that handler nor `loadRulesFromUrl` applies `canDownloadRulesWithCurrentState`; the downloader checks network availability and nonempty URL. Do not assert the comment's privacy guarantee from this path.
* Neither linkage API has a paired response, callback, or unconditional “message shown” consequence. Downloaded rules and future message interactions depend on independent rules/UI events.
* CA04 has **no EventHub dispatch**. Missing extras, sender code other than `750183`, null message or missing PendingIntent return without displaying; posting exceptions are logged/caught. Registering a delete PendingIntent does not mean a dismissal occurred.
* CA05 requires `NOTIFICATION_USER_INFO` with a non-null extracted map. It always sets fixed `"action":"2"` when delegating. Core, not the Campaign facade, publishes `Collect Data`.
* Campaign's OS handler further requires opt-in, ECID/server, and nonempty broadlog/delivery/action. It derives decimal message ID using `Integer.parseInt(deliveryId,16)`; malformed/overflowing values can throw before publication. `DataForMessageRequest` is published **before** the tracking request, not after successful HTTP.
* Internal in-app paths can publish `a.message.triggered`, `a.message.viewed` or `a.message.clicked` with string `"1"`. They are not unconditional linkage-call descendants.

Evidence: `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignExtension.java:186-217,490-600,642-743,767-817,838-846`; `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignState.java:152-165,197-208`; `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignRulesDownloader.java:80-137`; `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignConstants.java:114-119,154-158`. Core delegate: `aepsdk-core-android/code/core/src/phone/java/com/adobe/marketing/mobile/MobileCore.java:487-504`; name literal: `code/core/src/main/java/com/adobe/marketing/mobile/internal/CoreConstants.kt:104`.

#### Payload profiles

All U. CA.notificationOS and CA06 have no event templates.

```json
[
  {"profileId":"CA.set","eventName":"setLinkageFields Event","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"linkagefields":{"<crm-field>":"<crm-value>"}}},
  {"profileId":"CA.reset","eventName":"resetLinkageFields Event","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.requestReset","eventData":null},
  {"profileId":"CA.dismiss","eventName":"Collect Data","eventType":"com.adobe.eventType.generic.data","eventSource":"com.adobe.eventSource.os","eventData":{"broadlogId":"<broadlog-id-or-null>","deliveryId":"<hex-delivery-id-or-null>","action":"2"}},
  {"profileId":"CA.messageResponse","eventName":"DataForMessageRequest","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.responseContent","eventData":{"a.message.id":"<decimal-message-id>","a.message.clicked":"1"}}
]
```

### Campaign Classic

Repository: `aepsdk-campaignclassic-android`; version `3.1.4`. Facade file: `code/campaignclassic/src/phone/java/com/adobe/marketing/mobile/CampaignClassic.java`.

#### Complete API inventory

| ID | Class | Signature | Line | Profiles |
|---|---|---|---:|---|
| CC01 | CampaignClassic | `String extensionVersion()` | 51 | NO_EVENT |
| CC02 | CampaignClassic | `void registerDevice(String token, String userKey, Map<String, Object> additionalParams)` | 63 | CC.register, CC.registrationResponse |
| CC03 | CampaignClassic | `void trackNotificationReceive(Map<String, String> trackInfo)` | 108 | CC.receive |
| CC04 | CampaignClassic | `void trackNotificationClick(Map<String, String> trackInfo)` | 148 | CC.click |
| CC05 | AEPMessagingService | `boolean handleRemoteMessage(Context context, RemoteMessage remoteMessage)` | 53 in `code/campaignclassic/src/phone/java/com/adobe/marketing/mobile/AEPMessagingService.java` | CC.receive (delegation) |

#### Flows and conditions

```text
CC02 → CampaignClassic Register Device [U, campaign/requestContent]
  → configured extension handler → RegistrationManager
  → validation/dedup OR registration HTTP POST
  → Device Registration Status [U; no request pairing]
CC03 / CC04 → respective receive/click request [U]
  → TrackRequestManager validation → tracking HTTP GET → no EventHub tracking reply
CC05 → build push payload → construct/post Android notification
  → private helper → CC03 → same CC.receive profile (not a second distinct event)
```

* Null/empty token in CC02 or null/empty `trackInfo` in CC03/CC04: no family event. Nonempty tracking maps missing `_mId`/`_dId` **still dispatch**; downstream HTTP is then suppressed.
* Registration/tracking handling waits for configuration shared state (`readyForEvent`); no per-call callback or API-level timeout is promised.
* Registration requires opt-in, marketing server/integration key and network services. Invalid/missing fields, non-opt-in, null connection and non-200 produce status false. An unchanged persisted registration hash produces **true with no HTTP**. HTTP 200 also produces true and updates the persisted hash.
* `Device Registration Status` is not built with `inResponseToEvent`. Do not pair concurrent registration calls using timing alone, or interpret true solely as a newly successful remote registration.
* Tracking requires opt-in, tracking server and IDs. `_mId` must parse as UUID or supported signed/unsigned decimal number; decimal values are converted to hex. Missing/invalid values return without HTTP. HTTP status is logged; it does not produce a tracking response event.
* CC05 returns false on empty notification tag or caught `IllegalArgumentException` / `NotificationConstructionFailedException`; those paths do not call CC03. A true return means local message handling/posting, not completed tracking delivery. Android `NotificationManagerCompat.from(context)` occurs before the try block; annotations do not imply all null-argument errors are caught.
* Receive and click are separate events/flags, not overloads/aliases. A POC telemetry completion label inside receive happens to say `campaign.registerDevice`; that instrumentation label does not change the receive EventHub event.

Dispatch selectors: `code/campaignclassic/src/main/java/com/adobe/marketing/mobile/campaignclassic/internal/CampaignClassicExtension.kt:84-164`. Registration: `code/campaignclassic/src/main/java/com/adobe/marketing/mobile/campaignclassic/internal/RegistrationManager.kt:82-177,264-327`. Tracking: `code/campaignclassic/src/main/java/com/adobe/marketing/mobile/campaignclassic/internal/TrackRequestManager.kt:62-195`. Keys: `code/campaignclassic/src/main/java/com/adobe/marketing/mobile/campaignclassic/internal/CampaignClassicConstants.java:65-75`.

#### Payload profiles

All U. Caller-supplied additional parameters/extra trackInfo keys remain caller-defined; service delegation supplies `_mId`/`_dId` only.

```json
[
  {"profileId":"CC.register","eventName":"CampaignClassic Register Device","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.requestContent","eventData":{"registerdevice":true,"devicetoken":"<push-token>","userkey":"<user-key-or-null>","additionalparameters":{"<custom-key>":"<custom-value>"}}},
  {"profileId":"CC.receive","eventName":"CampaignClassic Track Notification Receive","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.requestContent","eventData":{"trackreceive":true,"trackinfo":{"_mId":"<message-id>","_dId":"<delivery-id>"}}},
  {"profileId":"CC.click","eventName":"CampaignClassic Track Notification Click","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.requestContent","eventData":{"trackclick":true,"trackinfo":{"_mId":"<message-id>","_dId":"<delivery-id>"}}},
  {"profileId":"CC.registrationResponse","eventName":"Device Registration Status","eventType":"com.adobe.eventType.campaign","eventSource":"com.adobe.eventSource.responseContent","eventData":{"registrationstatus":true}}
]
```

`registrationstatus` is a boolean, false on the documented failure branches; `additionalparameters` may be null. Campaign Standard and Classic intentionally share the **campaign event type**, but have different API owners, event names, sources and selector flags. A campaign-type-only metric cannot safely distinguish them.

### Target — UNINDEXED production evidence

Repository: `aepsdk-target-android`; version `3.0.2`. All source paths below were inspected natively after the bounded index recovery described above. No indexed impact/test assertion is made.

#### Complete facade inventory

Declaration file: `code/target/src/phone/java/com/adobe/marketing/mobile/Target.java`.

| ID | Signature | Line | Profiles |
|---|---|---:|---|
| TA01 | `String extensionVersion()` | 132 | NO_EVENT |
| TA02 | `void prefetchContent(List<TargetPrefetch> mboxPrefetchList, TargetParameters parameters, AdobeCallback<String> callback)` | 159 | TA.prefetch, TA.prefetchResponse |
| TA03 | `void retrieveLocationContent(List<TargetRequest> mboxRequestList, TargetParameters parameters)` | 268 | TA.load, TA.contentResponse, TA.a4t |
| TA04 | `void displayedLocations(List<String> mboxNames, TargetParameters targetParameters)` | 360 | TA.display, TA.a4t |
| TA05 | `void clickedLocation(String mboxName, TargetParameters parameters)` | 402 | TA.click, TA.a4t |
| TA06 | `void getThirdPartyId(AdobeCallback<String> callback)` | 443 | TA.getThird, TA.identityResponse |
| TA07 | `void setThirdPartyId(String thirdPartyId)` | 500 | TA.setThird |
| TA08 | `void getTntId(AdobeCallback<String> callback)` | 530 | TA.getTnt, TA.identityResponse |
| TA09 | `void setTntId(String tntId)` | 595 | TA.setTnt |
| TA10 | `void getSessionId(AdobeCallback<String> callback)` | 624 | TA.getSession, TA.identityResponse |
| TA11 | `void setSessionId(String sessionId)` | 688 | TA.setSession |
| TA12 | `void resetExperience()` | 712 | TA.reset |
| TA13 | `void clearPrefetchCache()` | 732 | TA.clear |
| TA14 | `void setPreviewRestartDeepLink(Uri deepLink)` | 754 | TA.preview |
| TA15 | `void executeRawRequest(Map<String, Object> request, AdobeCallback<Map<String, Object>> callback)` | 789 | TA.raw (DYNAMIC), TA.rawResponse |
| TA16 | `void sendRawNotifications(Map<String, Object> request)` | 879 | TA.rawNotifications (DYNAMIC) |

#### Complete models, builders and callback inventory

All source paths in this table have prefix `code/target/src/main/java/com/adobe/marketing/mobile/target/`. Each ID is also an individual manifest row; no model accessors are hidden in a count.

| ID | Class / source file | Signature | Line | Profile |
|---|---|---|---:|---|
| TP01 | TargetPrefetch.java | `TargetPrefetch(String mboxName, TargetParameters targetParameters)` | 51 | NO_EVENT |
| TP02 | TargetPrefetch.java | `Map<String, Object> toEventData()` | 61 | NO_EVENT |
| TR01 | TargetRequest.java | `TargetRequest(String mboxName, TargetParameters targetParameters, String defaultContent, AdobeCallback<String> contentCallback)` | 42 | NO_EVENT |
| TR02 | TargetRequest.java | `TargetRequest(String mboxName, TargetParameters targetParameters, String defaultContent, AdobeTargetDetailedCallback contentWithDataCallback)` | 63 | NO_EVENT |
| TR03 | TargetRequest.java | `String getMboxName()` | 79 | NO_EVENT |
| TR04 | TargetRequest.java | `String getDefaultContent()` | 97 | NO_EVENT |
| TR05 | TargetRequest.java | `AdobeCallback<String> getContentCallback()` | 115 | NO_EVENT |
| TR06 | TargetRequest.java | `AdobeTargetDetailedCallback getContentWithDataCallback()` | 124 | NO_EVENT |
| TR07 | TargetRequest.java | `void setResponsePairId(String pairId)` | 133 | NO_EVENT |
| TR08 | TargetRequest.java | `Map<String, Object> toEventData()` | 142 | NO_EVENT |
| TR09 | TargetRequest.java | `boolean equals(Object o)` | 204 | NO_EVENT |
| TR10 | TargetRequest.java | `int hashCode()` | 229 | NO_EVENT |
| TM01 | TargetParameters.java | `Map<String, String> getParameters()` | 51 | NO_EVENT |
| TM02 | TargetParameters.java | `Map<String, String> getProfileParameters()` | 60 | NO_EVENT |
| TM03 | TargetParameters.java | `TargetProduct getProduct()` | 69 | NO_EVENT |
| TM04 | TargetParameters.java | `TargetOrder getOrder()` | 78 | NO_EVENT |
| TM05 | TargetParameters.java | `Map<String, Object> toEventData()` | 223 | NO_EVENT |
| TM06 | TargetParameters.java | `boolean equals(Object o)` | 283 | NO_EVENT |
| TM07 | TargetParameters.java | `int hashCode()` | 298 | NO_EVENT |
| TB01 | TargetParameters.java / Builder | `Builder()` | 153 | NO_EVENT |
| TB02 | TargetParameters.java / Builder | `Builder(Map<String, String> parameters)` | 160 | NO_EVENT |
| TB03 | TargetParameters.java / Builder | `Builder parameters(Map<String, String> parameters)` | 170 | NO_EVENT |
| TB04 | TargetParameters.java / Builder | `Builder profileParameters(Map<String, String> profileParameters)` | 181 | NO_EVENT |
| TB05 | TargetParameters.java / Builder | `Builder product(TargetProduct product)` | 192 | NO_EVENT |
| TB06 | TargetParameters.java / Builder | `Builder order(TargetOrder order)` | 203 | NO_EVENT |
| TB07 | TargetParameters.java / Builder | `TargetParameters build()` | 213 | NO_EVENT |
| TO01 | TargetOrder.java | `TargetOrder(String id, double total, List<String> purchasedProductIds)` | 39 | NO_EVENT |
| TO02 | TargetOrder.java | `String getId()` | 51 | NO_EVENT |
| TO03 | TargetOrder.java | `double getTotal()` | 60 | NO_EVENT |
| TO04 | TargetOrder.java | `List<String> getPurchasedProductIds()` | 69 | NO_EVENT |
| TO05 | TargetOrder.java | `boolean equals(Object o)` | 134 | NO_EVENT |
| TO06 | TargetOrder.java | `int hashCode()` | 147 | NO_EVENT |
| TD01 | TargetProduct.java | `TargetProduct(String id, String categoryId)` | 36 | NO_EVENT |
| TD02 | TargetProduct.java | `String getId()` | 46 | NO_EVENT |
| TD03 | TargetProduct.java | `String getCategoryId()` | 55 | NO_EVENT |
| TD04 | TargetProduct.java | `boolean equals(Object o)` | 113 | NO_EVENT |
| TD05 | TargetProduct.java | `int hashCode()` | 123 | NO_EVENT |
| TC01 | AdobeTargetDetailedCallback.java | `void call(String content, Map<String, Object> data)` | 27 | CALLBACK_ONLY |
| TC02 | AdobeTargetDetailedCallback.java | `void fail(AdobeError error)` | 34 | CALLBACK_ONLY |

**Overloads and delegation:**

* TR01/TR02 are distinct constructors, each storing its corresponding callback. An untyped `null` fourth argument is ambiguous in Java; a typed callback/cast resolves it. They do not dispatch or delegate to one another.
* Builder methods store fields and return `this`; TB07 calls the **private** `TargetParameters(Builder)` constructor. Null parameter maps become empty maps. No builder method dispatches.
* Public `toEventData()` methods merely return maps; dispatch happens later in the facade. TR07 is a local mutator; TA03 replaces the pair ID with a generated UUID.
* `TargetRequest.getTargetParameters/getResponsePairId`, `TargetPrefetch.getMboxName/getTargetParameters`, `TargetOrder.toEventData`, `TargetProduct.toEventData`, and `fromEventData` helpers are package-private, not omitted public APIs.
* `TargetRequestBuilder` is package-private implementation infrastructure, not an additional customer request builder.
* Detailed callback methods are implicitly public interface contracts, not event publishers. TC01 receives optional data submaps; the inspected load listener calls `call` and catches callback exceptions. TC02's existence does not prove a failure callback is invoked by that listener.

#### Flows and rejection/error paths

```text
TA02 → TargetPrefetchRequest [U] → serialize prefetch/model parameters
  → extension selector → preview/config/privacy/network validation
  → HTTP delivery request → cache/update IDs
  → TargetPrefetchResponse [P] → callback(error-or-null)

TA03 → validate request list / local default callbacks for bad names
  → generate pair IDs + retain callbacks → TargetLoadRequest [U]
  → cached content OR batch HTTP OR default content
  → TargetRequestResponse [U + eventData pair IDs]
  → exact-name response listener → detailed callback or simple callback
  → conditional A4T path (separate event, not guaranteed)

TA04 / TA05 → display/click request [U]
  → configured opted-in Target + cached location + notification token
  → conditional AnalyticsForTargetRequest [U, Analytics type, Target publisher]
  → delivery notification HTTP (not an EventHub acknowledgment)

TA06 / TA08 / TA10 → respective requestIdentity [U]
  → one shared TargetResponseIdentity producer [P] → extract respective field
TA07 / TA09 / TA11 → respective setter [U] → persisted/local state, no paired reply
TA12 → TargetRequestReset [U] → clear IDs/session/edge host + shared state
TA13 → TargetClearPrefetchCache [U] → clear cache, no dedicated reply
TA14 → TargetSetPreviewRestartDeeplink [U] → set preview manager restart URI
TA15 → copy caller delivery map + israwevent=true → TargetRawRequest [U]
  → raw HTTP → TargetRawResponse [P, dynamic responsedata]
TA16 → copy caller notification map + israwevent=true → TargetRawNotifications [U]
  → notification-only raw HTTP → no raw response event
```

* TA02 null/empty list fails locally with **no dispatch**. Null entries are skipped; a nonempty all-null list reports an error **but lacks a return**, so an empty `prefetch` event is still dispatched. Preserve this distinction rather than describing all-invalid as uniformly rejected.
* TA02 accepts nullable callback by annotation, but normal response processing calls `callback.call(prefetchError)` without a null guard. Null callback is therefore not a reliably safe fire-and-forget path. Response timeout is 5000ms; failures are forwarded only to `AdobeCallbackWithError`.
* TA03 null/empty list or no valid requests produces no request event. Null requests are skipped. Invalid names invoke their own default callback locally, with no corresponding response event; other valid requests still coalesce into one load event.
* TA03 installs a response listener once and retains callbacks using `<load-event-uuid>-<responsePairId>`. It does **not** install the timeout described in its Javadoc. The listener validates exact event name and IDs, prefers detailed callback and catches callback exceptions.
* Config/client-code/non-opt-in errors in retrieval can produce **default-content** response events. Content/default-content arrival is not evidence that a remote decision was fetched.
* TA04 rejects null/empty name lists; TA05 rejects null/empty name; TA14 rejects null URI. Even accepted direct events may result in no HTTP when configured/privacy/cache/token requirements fail.
* Identity getter null callback: no dispatch. All three use a 5000ms paired callback and one response shape. Setters dispatch null/empty inputs and are not aliases of getters. Getter responses omit unavailable third-party/TnT IDs.
* Reset creates shared state but does **not** construct the unused `TargetReset` constant as a completion event.
* TA14 sets a restart URI; it does not itself enter preview mode or immediately show UI.
* Raw request map null/empty or missing both `execute`/`prefetch`: local error/null callback, no event. Raw notifications missing `notifications`: no event. Checks are key-presence checks, not full delivery schema validation.
* Raw APIs copy **all** caller map entries and overwrite `israwevent` to true. A caller can include both execute and prefetch, or additional keys. A notification map that also contains content-request fields can enter content-request handling; “no response” applies to notification-only input.
* `executeRawRequest` annotates callback non-null but does not enforce that before dispatch; its response handler dereferences callback. Do not infer input safety from annotations.

Source selectors/effects: `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetExtension.java:189-364,423-563,658-905,1024-1148,1260-1273,1338-1369,1453-1489,1669-1863,2122-2127,2185-2194`. Exact literal/key definitions: `code/target/src/phone/java/com/adobe/marketing/mobile/Target.java:45-107`; `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetConstants.java:121-195`.

#### Full Target parameter structure

The following is a **map serialization**, not an EventHub dispatch. It may appear as global `targetparams`, per-prefetch `targetparams`, or per-load-request `targetparams`. Omit a `targetparams` key if its model is null; omit `order`/`product` if absent. `parameters`/`profileParameters` are maps, empty permitted.

```json
{
  "parameters":{"<parameter-key>":"<parameter-value>"},
  "profileParameters":{"<profile-key>":"<profile-value>"},
  "order":{"id":"<order-id>","total":0,"purchasedProductIds":["<product-id>"]},
  "product":{"id":"<product-id>","categoryId":"<category-id>"}
}
```

Serialization evidence: `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetParameters.java:223-235`; `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetOrder.java:78-85`; `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetProduct.java:64-68`; `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetRequest.java:142-152`; `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetPrefetch.java:61-69`.

#### Direct Target payload profiles

All U. The shorter parameter maps below instantiate the shared shape above with optional order/product omitted, not a different serialization.

```json
[
  {"profileId":"TA.prefetch","eventName":"TargetPrefetchRequest","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"prefetch":[{"name":"<mbox-name>","targetparams":{"parameters":{},"profileParameters":{}}}],"targetparams":{"parameters":{},"profileParameters":{}}}},
  {"profileId":"TA.load","eventName":"TargetLoadRequest","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"request":[{"name":"<mbox-name>","defaultContent":"<fallback-content-or-null>","responsePairId":"<generated-pair-uuid>","targetparams":{"parameters":{},"profileParameters":{}}}],"targetparams":{"parameters":{},"profileParameters":{}}}},
  {"profileId":"TA.display","eventName":"TargetLocationsDisplayed","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"islocationdisplayed":true,"names":["<mbox-name>"],"targetparams":{"parameters":{},"profileParameters":{}}}},
  {"profileId":"TA.click","eventName":"TargetLocationClicked","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"islocationclicked":true,"name":"<mbox-name>","targetparams":{"parameters":{},"profileParameters":{}}}},
  {"profileId":"TA.getThird","eventName":"TargetGetThirdPartyIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"TA.setThird","eventName":"TargetSetThirdPartyIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"thirdpartyid":"<third-party-id-or-null>"}},
  {"profileId":"TA.getTnt","eventName":"TargetGetTnTIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"TA.setTnt","eventName":"TargetSetTnTIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"tntid":"<tnt-id-or-null>"}},
  {"profileId":"TA.getSession","eventName":"TargetGetSessionIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":null},
  {"profileId":"TA.setSession","eventName":"TargetSetSessionIdentifier","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestIdentity","eventData":{"sessionid":"<session-id-or-null>"}},
  {"profileId":"TA.reset","eventName":"TargetRequestReset","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestReset","eventData":{"resetexperience":true}},
  {"profileId":"TA.clear","eventName":"TargetClearPrefetchCache","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestReset","eventData":{"clearcache":true}},
  {"profileId":"TA.preview","eventName":"TargetSetPreviewRestartDeeplink","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"restartdeeplink":"<sanitized-uri>"}},
  {"profileId":"TA.raw","eventName":"TargetRawRequest","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"execute":{"<caller-delivery-key>":"<caller-delivery-value>"},"israwevent":true}},
  {"profileId":"TA.raw","eventName":"TargetRawRequest","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"prefetch":{"<caller-delivery-key>":"<caller-delivery-value>"},"israwevent":true}},
  {"profileId":"TA.rawNotifications","eventName":"TargetRawNotifications","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.requestContent","eventData":{"notifications":[{"<notification-key>":"<notification-value>"}],"israwevent":true}}
]
```

Raw examples are **DYNAMIC**, alternate minimal envelope shapes; arbitrary caller entries are preserved. No fabricated fixed schema is claimed for caller-controlled delivery content.

#### Downstream Target payload profiles

Identity/prefetch/raw response links P; load/A4T links U. Profile conditions, not array order, determine occurrence.

```json
[
  {"profileId":"TA.identityResponse","eventName":"TargetResponseIdentity","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.responseIdentity","eventData":{"thirdpartyid":"<third-party-id>","tntid":"<tnt-id>","sessionid":"<session-id>"}},
  {"profileId":"TA.prefetchResponse","eventName":"TargetPrefetchResponse","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.responseContent","eventData":{"prefetcherror":null,"prefetchresult":true}},
  {"profileId":"TA.contentResponse","eventName":"TargetRequestResponse","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.responseContent","eventData":{"content":"<target-content-or-default>","data":{"analytics.payload":{"<a4t-key>":"<a4t-value>"},"responseTokens":{"<response-token-key>":"<response-token-value>"},"clickmetric.analytics.payload":{"<a4t-key>":"<a4t-value>"}},"responsePairId":"<per-request-pair-uuid>","responseEventId":"<load-event-uuid>"}},
  {"profileId":"TA.rawResponse","eventName":"TargetRawResponse","eventType":"com.adobe.eventType.target","eventSource":"com.adobe.eventSource.responseContent","eventData":{"responsedata":{"<delivery-response-key>":"<delivery-response-value>"}}},
  {"profileId":"TA.a4t","eventName":"AnalyticsForTargetRequest","eventType":"com.adobe.eventType.analytics","eventSource":"com.adobe.eventSource.requestContent","eventData":{"contextdata":{"&&<a4t-key>":"<a4t-value>","a.target.sessionId":"<target-session-id>"},"action":"AnalyticsForTarget","trackinternal":true}}
]
```

* Prefetch errors use `prefetcherror:"<sanitized-error>"`, `prefetchresult:false`; success is null/true. Preview mode, invalid list, configuration/privacy, network, parse/status and missing-mbox errors route to this producer.
* Load data submaps are optional; default-content response uses `"data":{}`. `responsePairId` is added only when nonempty. Only responseEventId/pair ID matching locates the retained callback.
* Raw `responsedata` is a dynamic server map, or null on error. Raw notification-only requests do not create this reply.
* **Publisher Target, event type Analytics:** nonempty A4T payload on supported execute/display/click paths creates `AnalyticsForTargetRequest`. The response parser prefixes payload keys with `&&` and adds `a.target.sessionId` only if nonempty (`code/target/src/main/java/com/adobe/marketing/mobile/target/TargetResponseParser.java:238-269`). This is neither a direct Analytics customer API call nor a Target API completion response.
* If Analytics is registered and processes that internal request, it may queue another HTTP hit and eventually produce `AN.hitResponse`. That secondary chain is conditional; raw Target calls do not automatically generate this A4T event.

### Reconciliation, exclusions and reproducibility

#### Inventory closure

| Surface | Count | Reconciled to |
|---|---:|---|
| Analytics facade | 7 | explicit facade declarations |
| Audience facade | 4 | explicit facade declarations; public-class census, no public model/callback types |
| Campaign facade / Android receivers / compatibility model | 3 / 2 / 1 | source declarations and package accessibility |
| CampaignClassic facade / messaging helper | 4 / 1 | explicit declarations |
| Target facade | 16 | native source declarations, not README-only API list |
| TargetPrefetch / TargetRequest | 2 / 10 | constructors, accessors, mutator, serializer and overrides |
| TargetParameters / Builder | 7 / 7 | accessors, serializer, overrides, both constructors and all fluent/build methods |
| TargetOrder / TargetProduct | 6 / 5 | all public constructors/accessors/overrides |
| Target detailed callback | 2 | implicitly public interface methods |
| **Total** | **77** | every row has profiles and an individual manifest record |

No deprecated facade methods were declared in these checked-out versions; older `registerExtension`, historical Target APIs or model accessors were not invented. API baseline absence was reconciled to explicit declarations and tracked production source manifests, not treated as proof that README lists were complete.

**Excluded infrastructure:** Extension subclasses and extension-author lifecycle methods; analytics/internal types; Audience queue/state/network implementation; Campaign's package-private message/UI/notification classes; CampaignClassic internal push models/activities/receivers/managers; Target's package-private request builder/state/parser/JSON/preview utilities; optional telemetry bridges. Public constants/fields are not callable APIs. Core/Android/Firebase/notificationbuilder declarations belong to their owners. Tests/samples, inherited Object methods, compiler-provided implicit constructors and synthetic methods are excluded. Explicit public equality/hash overrides in Target models remain included.

#### Exact API-source SHA-256 snapshots

Paths are relative to the repository listed; fingerprints supplement HEAD/branch/dirty state and identify the actual declaration files read.

| Repository | Source | SHA-256 |
|---|---|---|
| analytics | `code/analytics/src/phone/java/com/adobe/marketing/mobile/Analytics.java` | `7cdf9003a6a1b6865882eb934a8c771b3ec7b4302cfd282bce643247cedd70c3` |
| audience | `code/audience/src/phone/java/com/adobe/marketing/mobile/Audience.java` | `742c57815aa5ae5cedd384eef564145b5442602dbd65c9bf8f150eb4cda7c167` |
| campaign | `code/campaign/src/phone/java/com/adobe/marketing/mobile/Campaign.java` | `bd5dc626838376296bdc87fca3140c1e29653b1a45897644c45e4e24de23b15d` |
| campaign | `code/campaign/src/phone/java/com/adobe/marketing/mobile/LocalNotificationHandler.java` | `a2669fefc4a46819131554f4e360ccf4df793f6ef79fca4acf8a774065d42081` |
| campaign | `code/campaign/src/phone/java/com/adobe/marketing/mobile/NotificationDismissalHandler.java` | `7c1e3693cc3ae86501562a407ede538f86202dfaf2d563c47c955a0e0f94203d` |
| campaign | `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignHit.java` | `36e1a27dfd4a890c2f938388be0c3b34af726998a060efb1cb8073562a326a91` |
| campaignclassic | `code/campaignclassic/src/phone/java/com/adobe/marketing/mobile/CampaignClassic.java` | `e6997f276b0e7c2b816e93c5180103114db8cf6f30eac71b27bcb120a208c279` |
| campaignclassic | `code/campaignclassic/src/phone/java/com/adobe/marketing/mobile/AEPMessagingService.java` | `feb76f55542ab513005b6564c891a8c12ac62216ac62309828fbe8d7028ac40f` |
| target | `code/target/src/phone/java/com/adobe/marketing/mobile/Target.java` | `0692f90f65e6aeecf5885ac6038832a02b0533a4eefdec4fd9a9710a84f3e4ee` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetPrefetch.java` | `bfbdb9eb682f29da01d9e8c5ac3859de0adad6df09c794b17be27d7515d24f51` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetRequest.java` | `5f4b2bf92a52ec700e0c1f159901f6eee48ca8141f9186604dbc40a710fbceb0` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetParameters.java` | `3da1490c626cc97cb4248c84e8e13b2432662d815d1aa533cef5ad6979ee7ab1` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetOrder.java` | `d86a2895d428639168c9c70fbddb936a20a82293a70abbbe9d264ada0cbcc412` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/TargetProduct.java` | `7d9cb33f6bbd14f74accb77f96503d1bc8fe449cc9167c2c7463366e20fbfae6` |
| target | `code/target/src/main/java/com/adobe/marketing/mobile/target/AdobeTargetDetailedCallback.java` | `cadecb020ab2f4322e109008e93609bf87469ef7018e40023a3cad6f70350310` |

Reviewed Campaign downloader SHA-256: `6b9d726d0eb70e52cf1b4ec266d42577af5a8b6d8fcd5b374325e82bad0aeb26` for `code/campaign/src/main/java/com/adobe/marketing/mobile/campaign/CampaignRulesDownloader.java`.

#### Coverage / handoff

| Criterion | Status | Strongest exact evidence | Confidence | Gap |
|---|---|---|---|---|
| Five repositories completed | Complete | Manifest `repositories`, 77 `apiInventory` rows | High | None for enumerated snapshot |
| Every API/overload linked | Complete | Individual ID tables above and manifest `profileIds` | High | No historical-version claim |
| Literal payload envelopes | Complete | Producer/key `sourceRefs` on all 45 profiles | High for inspected sources | Target evidence UNINDEXED |
| Models/builders/callbacks | Complete | Target declaration tables; Campaign package-accessibility reconciliation | High | Infrastructure deliberately excluded |
| Rejection/delegation/error distinctions | Complete | Facade guards and downstream handlers cited per extension | High | External application callbacks/rules not exhaustively predicted |
| Target indexed evidence | UNINDEXED | Attach/retry, resumed investigation, scoped `code/target` closure | High that fallback was required | Index-specific impact/tests unavailable |

**Changed-file manifest:** `classic-family.md`, `classic-family.json` only in the assigned artifact root. **Risks:** branch-specific POC telemetry and source/Javadoc discrepancies; Target index omission; API arrival/response is not business success. **Suggested checks:** parse JSON; require unique API/profile IDs; resolve all references; require all 77 inventory IDs in Markdown; parse all fenced JSON templates; compare exact declaration lines and snapshot fingerprints. No SDK build/runtime validation is required or claimed.

**Executed artifact validation: PASS.** Both assigned files only: 77 unique API IDs; 45 unique profiles; all profile references resolve; every API ID and event profile appears in Markdown; declaration lines name the expected public callable (or implicitly public interface method); cited source files exist; all seven fenced JSON templates parse. No shared/parent artifact validation or SDK builds were run.

---

<a id="family-media-optimize"></a>

## Media, Edge Media and Optimize — complete public API / EventHub fragment

**Summary:** 106 inventory entries across three Android repositories, including both complete MediaTracker interfaces, deprecated overloads, models/builders, list-offer APIs and callback/error contracts. The companion manifest defines 21 shared profiles and 86 normalized payload examples.

### Snapshots and evidence

All paths below are repository-relative. Snapshot date: **2026-11-19**.

| Repository | HEAD | Branch | Version | Source status |
|---|---|---|---|---|
| `aepsdk-media-android` | `421ea167840796935dc1cd69c503897c6b5de797` | `poc/telemetry-integration` | 3.1.2 | clean |
| `aepsdk-edgemedia-android` | `abe1677e93f850bcc88ef1c14bbfea759f1ce9d6` | `poc/telemetry-integration` | 3.0.1 | three pre-existing modified testapp files; library source clean |
| `aepsdk-optimize-android` | `6418d61d62cea704b3067ef043244c992e2bdfa0` | `poc/telemetry-integration` | 3.7.0 | pre-existing untracked `code/optimize/src/main/resources/`; tracked library source clean |

Scout initially reported all three unattached. One `scout attach --no-config` per repository and one successful search retry resolved access. `ensure_fresh` verified their tracked manifests, with zero reindexed gap files. None required **UNINDEXED** fallback. Source-state IDs and exact dirty paths are recorded in `media-optimize.json`. Derived symbol/vector views were not certified by `ensure_fresh`; all cited canonical files were also read directly. No repository/ancestor `AGENTS.md` or checked-in `.api`, `current.txt`, `public.txt`, `api.txt` baseline was found. Existing README/API-reference documentation was not treated as an exhaustive baseline.

Supporting sources, **not additional customer API inventories**:

* Core: `aepsdk-core-android@3a2eb50e437742e9738222bcc7e9099c4af32481`, branch `poc/telemetry-integration`, for EventType/EventSource/Event.Builder and sanitization.
* Edge: `aepsdk-edge-android@b10baf2515eda20a6cb78b8c424b85c3b6947fb2`, same branch, for bounded handle/error/completion response-builder tracing. Scout indexed/fresh; exact dirty status and source-state ID recorded in manifest. No relevant response producer file is modified.

#### Path abbreviations used in evidence

| Prefix | Repository and directory |
|---|---|
| `M/` | media: `code/media/src/phone/java/com/adobe/marketing/mobile/` |
| `MI/` | media: `code/media/src/main/java/com/adobe/marketing/mobile/media/internal/` |
| `E/` | edgemedia: `code/edgemedia/src/phone/java/com/adobe/marketing/mobile/edge/media/` |
| `EI/` | edgemedia: `code/edgemedia/src/main/java/com/adobe/marketing/mobile/edge/media/internal/` |
| `O/` | optimize: `code/optimize/src/main/java/com/adobe/marketing/mobile/optimize/` |
| `C/` | core: `code/core/src/main/java/com/adobe/marketing/mobile/` |
| `N/` | edge: `code/edge/src/main/java/com/adobe/marketing/mobile/` |

#### Reading the inventory

Signatures omit Java `public`, annotations and `final`, but preserve argument/return types, overloads and explicit overrides. `M` classes belong to `com.adobe.marketing.mobile`; `E` classes to `com.adobe.marketing.mobile.edge.media`; `O` classes to `com.adobe.marketing.mobile.optimize`.

* `*.LOCAL` = **NO_EVENT** after tracing; factories/serialization can supply a later event, but do not publish one.
* `*.TRACK` = **DYNAMIC**, finite `Media.Event`/API-to-`event.name` mapping, not arbitrary event names.
* `O.CALLBACK`, `O.LISTEN` = **CALLBACK_ONLY**; the callback/listener declaration does not publish an event.
* Other profiles distinguish direct requests from **conditional** downstream publication. Profile membership never means every listed payload occurs on every call.
* Media parameters use JetBrains annotations; Edge Media uses AndroidX. Session info, event enum, error ID and QoE map are nonnull; generic `trackEvent` info and metadata are nullable. Optimize scope lists and get/update-listener callbacks are nonnull; update callback, XDM and free-form data are nullable.

### API inventory — Media (20)

| ID | Owner | Explicit signature | Profile(s) | Declaration |
|---|---|---|---|---|
| M01 | Media | `static MediaTracker createTracker()` | M.CREATE | M/Media.java:33 |
| M02 | Media | `static MediaTracker createTracker(Map<String, Object> config)` | M.CREATE | M/Media.java:44 |
| M03 | Media | `static void createTracker(AdobeCallback<MediaTracker> callback)` **deprecated** | M.CREATE | M/Media.java:55 |
| M04 | Media | `static void createTracker(Map<String, Object> config, AdobeCallback<MediaTracker> callback)` **deprecated** | M.CREATE | M/Media.java:68 |
| M05 | Media | `static String extensionVersion()` | M.LOCAL | M/Media.java:80 |
| M06 | Media | `static HashMap<String, Object> createMediaObject(String name, String mediaId, double length, String streamType, MediaType mediaType)` | M.LOCAL | M/Media.java:95 |
| M07 | Media | `static HashMap<String, Object> createAdBreakObject(String name, long position, double startTime)` | M.LOCAL | M/Media.java:112 |
| M08 | Media | `static HashMap<String, Object> createAdObject(String name, String adId, long position, double length)` | M.LOCAL | M/Media.java:126 |
| M09 | Media | `static HashMap<String, Object> createChapterObject(String name, long position, double length, double startTime)` | M.LOCAL | M/Media.java:143 |
| M10 | Media | `static HashMap<String, Object> createQoEObject(long bitrate, double startupTime, double fps, long droppedFrames)` | M.LOCAL | M/Media.java:160 |
| M11 | Media | `static HashMap<String, Object> createStateObject(String stateName)` | M.LOCAL | M/Media.java:174 |
| M12 | MediaTracker | `void trackSessionStart(Map<String, Object> mediaInfo, Map<String, String> contextData)` | M.TRACK, M.SESSION_CREATED | M/MediaTracker.java:25 |
| M13 | MediaTracker | `void trackPlay()` | M.TRACK | M/MediaTracker.java:29 |
| M14 | MediaTracker | `void trackPause()` | M.TRACK | M/MediaTracker.java:32 |
| M15 | MediaTracker | `void trackComplete()` | M.TRACK | M/MediaTracker.java:38 |
| M16 | MediaTracker | `void trackSessionEnd()` | M.TRACK | M/MediaTracker.java:44 |
| M17 | MediaTracker | `void trackError(String errorId)` | M.TRACK | M/MediaTracker.java:51 |
| M18 | MediaTracker | `void trackEvent(Media.Event event, Map<String, Object> info, Map<String, String> contextData)` | M.TRACK | M/MediaTracker.java:60 |
| M19 | MediaTracker | `void updateQoEObject(Map<String, Object> qoeData)` | M.TRACK | M/MediaTracker.java:72 |
| M20 | MediaTracker | `void updateCurrentPlayhead(double time)` | M.TRACK | M/MediaTracker.java:81 |

### API inventory — Edge Media (18)

| ID | Owner | Explicit signature | Profile(s) | Declaration |
|---|---|---|---|---|
| E01 | Media | `static MediaTracker createTracker()` | E.CREATE | E/Media.java:36 |
| E02 | Media | `static MediaTracker createTracker(Map<String, Object> config)` | E.CREATE | E/Media.java:50 |
| E03 | Media | `static String extensionVersion()` | E.LOCAL | E/Media.java:62 |
| E04 | Media | `static HashMap<String, Object> createMediaObject(String name, String id, int length, String streamType, MediaType mediaType)` | E.LOCAL | E/Media.java:77 |
| E05 | Media | `static HashMap<String, Object> createAdBreakObject(String name, int position, int startTime)` | E.LOCAL | E/Media.java:94 |
| E06 | Media | `static HashMap<String, Object> createAdObject(String name, String id, int position, int length)` | E.LOCAL | E/Media.java:108 |
| E07 | Media | `static HashMap<String, Object> createChapterObject(String name, int position, int length, int startTime)` | E.LOCAL | E/Media.java:125 |
| E08 | Media | `static HashMap<String, Object> createQoEObject(int bitrate, int startupTime, int fps, int droppedFrames)` | E.LOCAL | E/Media.java:139 |
| E09 | Media | `static HashMap<String, Object> createStateObject(String stateName)` | E.LOCAL | E/Media.java:150 |
| E10 | MediaTracker | `void trackSessionStart(Map<String, Object> mediaInfo, Map<String, String> contextData)` | E.TRACK, E.XDM | E/MediaTracker.java:25 |
| E11 | MediaTracker | `void trackPlay()` | E.TRACK, E.XDM | E/MediaTracker.java:29 |
| E12 | MediaTracker | `void trackPause()` | E.TRACK, E.XDM | E/MediaTracker.java:32 |
| E13 | MediaTracker | `void trackComplete()` | E.TRACK, E.XDM | E/MediaTracker.java:38 |
| E14 | MediaTracker | `void trackSessionEnd()` | E.TRACK, E.XDM | E/MediaTracker.java:44 |
| E15 | MediaTracker | `void trackError(String errorId)` | E.TRACK, E.XDM | E/MediaTracker.java:51 |
| E16 | MediaTracker | `void trackEvent(Media.Event event, Map<String, Object> info, Map<String, String> contextData)` | E.TRACK, E.XDM | E/MediaTracker.java:60 |
| E17 | MediaTracker | `void updateQoEObject(Map<String, Object> qoeInfo)` | E.TRACK, E.XDM | E/MediaTracker.java:72 |
| E18 | MediaTracker | `void updateCurrentPlayhead(int time)` | E.TRACK, E.XDM | E/MediaTracker.java:81 |

### API inventory — Optimize (68)

Kotlin source properties are included once; their generated JVM accessors, data-class `copy/componentN/equals/hashCode/toString` and default-mask constructors are not separately counted. `@JvmStatic` aliases are conveniences, not extra source overloads.

| ID | Owner | Explicit signature | Profile(s) | Declaration |
|---|---|---|---|---|
| O01 | Optimize | `static String extensionVersion()` | O.LOCAL | O/Optimize.java:42 |
| O02 | Optimize | `static void updatePropositions(List<DecisionScope> decisionScopes, Map<String, Object> xdm, Map<String, Object> data)` **deprecated** | O.UPDATE | O/Optimize.java:62 |
| O03 | Optimize | `static void updatePropositions(List<DecisionScope> decisionScopes, Map<String, Object> xdm, Map<String, Object> data, AdobeCallback<Map<DecisionScope, OptimizeProposition>> callback)` | O.UPDATE | O/Optimize.java:89 |
| O04 | Optimize | `static void updatePropositions(List<DecisionScope> decisionScopes, Map<String, Object> xdm, Map<String, Object> data, double timeoutSeconds, AdobeCallback<Map<DecisionScope, OptimizeProposition>> callback)` | O.UPDATE | O/Optimize.java:118 |
| O05 | Optimize | `static void getPropositions(List<DecisionScope> decisionScopes, AdobeCallback<Map<DecisionScope, OptimizeProposition>> callback)` | O.GET | O/Optimize.java:283 |
| O06 | Optimize | `static void getPropositions(List<DecisionScope> decisionScopes, double timeoutSeconds, AdobeCallback<Map<DecisionScope, OptimizeProposition>> callback)` | O.GET | O/Optimize.java:299 |
| O07 | Optimize | `static void onPropositionsUpdate(AdobeCallback<Map<DecisionScope, OptimizeProposition>> callback)` | O.LISTEN, O.NOTIFICATION | O/Optimize.java:428 |
| O08 | Optimize | `static void clearCachedPropositions()` | O.CLEAR | O/Optimize.java:478 |
| O09 | DecisionScope | `DecisionScope(String name)` | O.LOCAL | O/DecisionScope.java:39 |
| O10 | DecisionScope | `DecisionScope(String activityId, String placementId)` | O.LOCAL | O/DecisionScope.java:53 |
| O11 | DecisionScope | `DecisionScope(String activityId, String placementId, int itemCount)` | O.LOCAL | O/DecisionScope.java:66 |
| O12 | DecisionScope | `String getName()` | O.LOCAL | O/DecisionScope.java:76 |
| O13 | DecisionScope | `boolean equals(Object o)` | O.LOCAL | O/DecisionScope.java:302 |
| O14 | DecisionScope | `int hashCode()` | O.LOCAL | O/DecisionScope.java:311 |
| O15 | OptimizeProposition | `String getId()` | O.LOCAL | O/OptimizeProposition.java:72 |
| O16 | OptimizeProposition | `List<Offer> getOffers()` | O.LOCAL | O/OptimizeProposition.java:81 |
| O17 | OptimizeProposition | `String getScope()` | O.LOCAL | O/OptimizeProposition.java:90 |
| O18 | OptimizeProposition | `Map<String, Object> getScopeDetails()` | O.LOCAL | O/OptimizeProposition.java:99 |
| O19 | OptimizeProposition | `Map<String, Object> getActivity()` | O.LOCAL | O/OptimizeProposition.java:108 |
| O20 | OptimizeProposition | `Map<String, Object> getPlacement()` | O.LOCAL | O/OptimizeProposition.java:117 |
| O21 | OptimizeProposition | `Map<String, Object> generateReferenceXdm()` | O.LOCAL | O/OptimizeProposition.java:129 |
| O22 | OptimizeProposition | `static OptimizeProposition fromEventData(Map<String, Object> data)` | O.LOCAL | O/OptimizeProposition.java:153 |
| O23 | OptimizeProposition | `boolean equals(Object o)` | O.LOCAL | O/OptimizeProposition.java:248 |
| O24 | OptimizeProposition | `int hashCode()` | O.LOCAL | O/OptimizeProposition.java:266 |
| O25 | Offer.Builder | `Builder(String id, OfferType type, String content)` | O.LOCAL | O/Offer.java:63 |
| O26 | Offer.Builder | `Builder setEtag(String etag)` | O.LOCAL | O/Offer.java:85 |
| O27 | Offer.Builder | `Builder setScore(double score)` | O.LOCAL | O/Offer.java:100 |
| O28 | Offer.Builder | `Builder setSchema(String schema)` | O.LOCAL | O/Offer.java:115 |
| O29 | Offer.Builder | `Builder setMeta(Map<String, Object> meta)` | O.LOCAL | O/Offer.java:130 |
| O30 | Offer.Builder | `Builder setLanguage(List<String> language)` | O.LOCAL | O/Offer.java:145 |
| O31 | Offer.Builder | `Builder setCharacteristics(Map<String, String> characteristics)` | O.LOCAL | O/Offer.java:161 |
| O32 | Offer.Builder | `Offer build()` | O.LOCAL | O/Offer.java:173 |
| O33 | Offer | `String getId()` | O.LOCAL | O/Offer.java:192 |
| O34 | Offer | `String getEtag()` | O.LOCAL | O/Offer.java:201 |
| O35 | Offer | `double getScore()` | O.LOCAL | O/Offer.java:210 |
| O36 | Offer | `String getSchema()` | O.LOCAL | O/Offer.java:219 |
| O37 | Offer | `Map<String, Object> getMeta()` | O.LOCAL | O/Offer.java:228 |
| O38 | Offer | `OfferType getType()` | O.LOCAL | O/Offer.java:237 |
| O39 | Offer | `List<String> getLanguage()` | O.LOCAL | O/Offer.java:246 |
| O40 | Offer | `String getContent()` | O.LOCAL | O/Offer.java:255 |
| O41 | Offer | `Map<String, String> getCharacteristics()` | O.LOCAL | O/Offer.java:264 |
| O42 | Offer | `OptimizeProposition getProposition()` | O.LOCAL | O/Offer.java:273 |
| O43 | Offer | `void displayed()` | O.DISPLAY | O/Offer.java:283 |
| O44 | Offer | `void tapped()` | O.TAP | O/Offer.java:293 |
| O45 | Offer | `Map<String, Object> generateDisplayInteractionXdm()` | O.LOCAL | O/Offer.java:316 |
| O46 | Offer | `Map<String, Object> generateTapInteractionXdm()` | O.LOCAL | O/Offer.java:357 |
| O47 | Offer | `boolean equals(Object o)` | O.LOCAL | O/Offer.java:532 |
| O48 | Offer | `int hashCode()` | O.LOCAL | O/Offer.java:552 |
| O49 | OfferType | `String toString()` | O.LOCAL | O/OfferType.java:22 |
| O50 | OfferType | `static OfferType from(String format)` | O.LOCAL | O/OfferType.java:47 |
| O51 | OfferUtils | `fun List<Offer>.displayed()` | O.DISPLAY | O/OfferUtils.kt:25 |
| O52 | OfferUtils | `fun List<Offer>.generateDisplayInteractionXdm(): Map<String, Any>?` | O.LOCAL | O/OfferUtils.kt:48 |
| O53 | AdobeCallbackWithOptimizeError&lt;T&gt; | `void fail(AEPOptimizeError error)` | O.CALLBACK | O/AdobeCallbackWithOptimizeError.java:17 |
| O54 | AdobeCallbackWithOptimizeError&lt;T&gt; | `void call(T value) (inherited from AdobeCallback<T>)` | O.CALLBACK | O/AdobeCallbackWithOptimizeError.java:16 |
| O55 | AEPOptimizeError | `AEPOptimizeError(type: String? = "", status: Int? = 0, title: String? = "", detail: String? = "", report: Map<String, Any>?, adobeError: AdobeError?)` | O.LOCAL | O/AEPOptimizeError.kt:26 |
| O56 | AEPOptimizeError | `val type: String?` | O.LOCAL | O/AEPOptimizeError.kt:27 |
| O57 | AEPOptimizeError | `val status: Int?` | O.LOCAL | O/AEPOptimizeError.kt:28 |
| O58 | AEPOptimizeError | `val title: String?` | O.LOCAL | O/AEPOptimizeError.kt:29 |
| O59 | AEPOptimizeError | `val detail: String?` | O.LOCAL | O/AEPOptimizeError.kt:30 |
| O60 | AEPOptimizeError | `var report: Map<String, Any>?` | O.LOCAL | O/AEPOptimizeError.kt:31 |
| O61 | AEPOptimizeError | `var adobeError: AdobeError?` | O.LOCAL | O/AEPOptimizeError.kt:32 |
| O62 | AEPOptimizeError | `fun toEventData(): Map<String, Any?>` | O.LOCAL | O/AEPOptimizeError.kt:35 |
| O63 | AEPOptimizeError.Companion | `fun AdobeError.toEventData(): Map<String, Any?>` | O.LOCAL | O/AEPOptimizeError.kt:65 |
| O64 | AEPOptimizeError.Companion | `fun toAEPOptimizeError(data: Map<String, Any?>): AEPOptimizeError` | O.LOCAL | O/AEPOptimizeError.kt:71 |
| O65 | AEPOptimizeError.Companion | `fun toAdobeError(data: Map<String, Any?>): AdobeError` | O.LOCAL | O/AEPOptimizeError.kt:83 |
| O66 | AEPOptimizeError.Companion | `fun getTimeoutError(): AEPOptimizeError` | O.LOCAL | O/AEPOptimizeError.kt:88 |
| O67 | AEPOptimizeError.Companion | `fun getUnexpectedError(): AEPOptimizeError` | O.LOCAL | O/AEPOptimizeError.kt:99 |
| O68 | AEPOptimizeError.Companion | `fun getInvalidRequestError(): AEPOptimizeError` | O.LOCAL | O/AEPOptimizeError.kt:110 |

Java aliases for O51/O52: `static void OfferUtils.displayed(List<Offer> offers)` and `static Map<String, Object> OfferUtils.generateDisplayInteractionXdm(List<Offer> offers)`. O64/O65/O66 also have `@JvmStatic` bridges. There is **no** public list `tapped()` API in this snapshot.

### Event envelope and correlation

Examples are **normalized illustrative EventHub payloads**, not literal runtime dumps or `sdk.usage` uploads. Angle-bracket values are safe placeholders; numbers are harmless examples, not customer data. Fixed protocol/schema URIs are SDK literals, not actual customer URLs. Optional entries are described explicitly rather than assumed always present.

`C/Event.java:83-92,153-163,195-224` verifies:

* Every Event.Builder creates a new UUID `eventId`; build supplies current epoch-millisecond EventHub timestamp.
* Unless a builder explicitly chains/responds, `parentId: null`, `responseId: null`. This includes direct Media requests, classic `SessionCreated`, all Edge Media requests/XDM children, Optimize notifications, clear, track and interaction requests.
* Optimize Edge personalization request: parent is original Optimize update request, responseId null.
* Optimize response: `parentId` **and** `responseId` are original Optimize get/update request ID.
* Optimize update-complete: parent is original Optimize update request; responseId null. Its data field `completedUpdateRequestForEventId` instead contains the **Edge** request ID.
* Edge handle/error: parent is attributed Edge request ID or null; responseId null. Edge completion: parent and responseId are Edge request ID.
* `trackerid`, client `sessionid`, backend `sessionID`/`mediaservice.sessionid`, `requestEventId` and HTTP `requestId` are distinct data fields, not interchangeable with EventHub links.
* Media `event.timestamp` is tracker sample time (Long milliseconds). Edge Media XDM timestamp is media reference time serialized ISO8601 UTC with milliseconds; it need not equal EventHub build timestamp.

### Media and Edge Media direct contracts

#### Flow and ownership

```text
Media.createTracker [M01–M04]
  → MediaTrackerEventGenerator.create → M.CREATE (MobileCore dispatch)
  → MediaExtension.handleMediaTrackerRequestEvent → MediaCollectionTracker

MediaTracker method [M12–M20]
  → eventToString/trackInternal → M.TRACK (MobileCore dispatch)
  → MediaExtension.handleMediaTrackEvent → MediaCollectionTracker rule engine
  → MediaCollectionHitGenerator
  → real-time MediaRealTimeService → MediaSession → HTTP media collection service
      ↳ conditional 2xx sessionStart + Location → M.SESSION_CREATED
  OR downloaded-content MediaOfflineService → persistent database/upload path

edge.media.Media.createTracker [E01–E02]
  → MediaTrackerEventGenerator.create → E.CREATE (MobileCore dispatch)
  → MediaExtension creates MediaEventTracker

edge.media.MediaTracker [E10–E18]
  → trackInternal → E.TRACK (MobileCore dispatch)
  → MediaEventTracker rules → MediaXDMEventGenerator → MediaEventProcessor
  → MediaRealTimeSession → E.XDM (Edge Media publishes an Edge-typed event)
  → Edge extension → backend
  ← Edge-produced media-analytics:new-session/error event (N.RESPONSES)
  → session queue resumes or aborts
```

Evidence: `M/MediaTrackerEventGenerator.java:106-148,155-264,306-355`; `MI/MediaExtension.java:132-181,208-243`; `MI/MediaCollectionTracker.java:88-175,722-979`; `MI/MediaSession.java:205-268`; `E/internal/MediaTrackerEventGenerator.java:60-88,95-212,258-306`; `EI/MediaExtension.java:76-94,103-144,178-247`; `EI/MediaEventTracker.java:88-169,719-1003`; `EI/MediaRealTimeSession.kt:137-195`.

Publishing a tracker request does not mean a tracker/session exists in the extension yet. Both extension handlers can drop missing tracker IDs or unknown trackers. Media APIs are Android calls, not HTTP routes: there is no HTTP status returned to these callers.

#### M.CREATE / E.CREATE

```json
[
  {
    "eventName": "Media::CreateTrackerRequest",
    "eventType": "com.adobe.eventType.media",
    "eventSource": "com.adobe.eventsource.media.requesttracker",
    "eventData": {
      "trackerid": "<tracker-uuid>",
      "event.param": {"config.channel": "<channel>", "config.downloadedcontent": false}
    }
  },
  {
    "eventName": "Edge Media CreateTrackerRequest",
    "eventType": "com.adobe.eventType.edgeMedia",
    "eventSource": "com.adobe.eventSource.createTracker",
    "eventData": {
      "trackerid": "<tracker-uuid>",
      "event.param": {"config.channel": "<channel>", "config.adpinginterval": 1, "config.mainpinginterval": 10}
    }
  }
]
```

* Media null config produces **empty** `event.param`; only Boolean/String config values survive filtering. A null value reaches `value.getClass()` in the unsupported-value log and can throw before publication (`M/MediaTrackerEventGenerator.java:110-125`). `config.downloadedcontent` selects offline service (`MI/MediaExtension.java:208-228`).
* Media deprecated callbacks are called **synchronously** with the newly constructed tracker, after dispatch; not after backend session creation (`M/Media.java:55-72`). No `fail` contract is implemented there.
* Edge Media null config **omits** `event.param`; nonnull config passes through Event.Builder. `config.adpinginterval` Integer accepts 1–10 seconds; `config.mainpinginterval` Integer accepts 10–50 seconds; invalid/unset uses 10 seconds (`EI/MediaXDMEventGenerator.kt:292-305`). Only real-time sessions are constructed (`EI/MediaEventProcessor.kt:41-46`).
* Exact namespaces are case-sensitive source values: classic Media custom sources contain lowercase `eventsource`; Edge Media Core sources contain `eventSource`. Core constants: `C/EventType.java:36,46`, `C/EventSource.java:46-47`.

#### M.LOCAL / E.LOCAL — information factories and types

Factories return public `HashMap<String,Object>` values; `MediaInfo`, `AdInfo`, `AdBreakInfo`, `ChapterInfo`, `QoEInfo` and `StateInfo` classes themselves are internal implementation types, **not omitted customer constructors/builders**. No separate public Media configuration/info/state builder exists.

| Object / exact map keys | Media Java value types | Edge Media Java value types |
|---|---|---|
| `media.name`, `media.id`, `media.streamtype` | String | String |
| `media.type` | String `"video"` or `"audio"` from MediaType.Video/Audio | same |
| `media.length` | Double | Integer |
| `media.resumed` | Boolean, factory false | same |
| `media.prerollwaitingtime` | Long, factory 250 ms | Integer, factory 250 ms |
| `media.granularadtracking` | optional caller-added Boolean; not returned by factory | same |
| `adbreak.name` | String | String |
| `adbreak.position`, `adbreak.starttime` | Long, Double | Integer, Integer |
| `ad.name`, `ad.id` | String | String |
| `ad.position`, `ad.length` | Long, Double | Integer, Integer |
| `chapter.name` | String | String |
| `chapter.position`, `chapter.length`, `chapter.starttime` | Long, Double, Double | Integer, Integer, Integer |
| `qoe.bitrate`, `qoe.startuptime`, `qoe.fps`, `qoe.droppedframes` | **all Double**, including widened long bitrate/droppedFrames arguments | all Integer |
| `state.name` | String matching `^[a-zA-Z0-9_.]{1,64}$` | same |
| `time.playhead` (tracker update) | Double | Integer |

Factories validate nonempty required IDs/names/stream type, nonnegative length/time/QoE values, positions ≥1, and valid state-name syntax; rejected object construction yields an empty map rather than an emitted failure event. No exception/fail callback is promised for these factories. `extensionVersion()` simply returns the snapshot version.

Evidence: `MI/MediaObject.java:30-129,230-243,489-496,622-628,749-756,893-900,1035-1068`; `EI/MediaObject.java:31-127,228-241,486-493,619-625,746-753,887-894,1026-1057`; exact keys `MI/MediaInternalConstants.java:89-188`, `EI/MediaInternalConstants.java:50-176`.

#### M.TRACK / E.TRACK — shared event envelope

```json
[
  {
    "eventName": "Media::TrackMedia",
    "eventType": "com.adobe.eventType.media",
    "eventSource": "com.adobe.eventsource.media.trackmedia",
    "eventData": {
      "trackerid": "<tracker-uuid>",
      "sessionid": "<client-session-uuid>",
      "event.name": "play",
      "event.internal": false,
      "event.timestamp": "<Long epoch-ms>"
    }
  },
  {
    "eventName": "Edge Media TrackMedia",
    "eventType": "com.adobe.eventType.edgeMedia",
    "eventSource": "com.adobe.eventSource.trackMedia",
    "eventData": {
      "trackerid": "<tracker-uuid>",
      "sessionid": "<client-session-uuid>",
      "event.name": "play",
      "event.internal": false,
      "event.timestamp": "<Long epoch-ms>"
    }
  }
]
```

Every call uses that extension's envelope. Substitute only the **literal** `event.name` in the following table and add the indicated `event.param` map. The companion JSON expands **all 23 variants per extension** into complete envelopes. `event.param`/`event.metadata` are omitted when null, included when nonnull (even empty). `event.metadata` is a `Map<String,String>` of caller context, available on sessionStart and every `trackEvent` enum. A caller-supplied `info` map is forwarded even if the corresponding rule ignores it; “none” below describes customary/consumed info, not a facade-level filter.

| Source API / Media.Event | Exact `event.name` | Consumed `event.param` keys | Conditional Edge XDM effect |
|---|---|---|---|
| trackSessionStart | `sessionstart` | mediaInfo keys above | sessionStart; playback/ping processing |
| trackPlay | `play` | none | play or coalesced ping |
| trackPause | `pause` | none | pauseStart or coalesced ping |
| trackComplete | `complete` | none | cleanup, sessionComplete |
| trackSessionEnd | `sessionend` | none | cleanup, sessionEnd |
| trackError | `error` | `error.id`: String | error with source `"player"` |
| updateQoEObject | `qoeupdate` | four QoE keys above | cache QoE; later attachment, not a qoeupdate endpoint |
| updateCurrentPlayhead | `playheadupdate` | `time.playhead`: Double / Integer | update clock; possible playback/ping/idle work |
| AdBreakStart | `adbreakstart` | `adbreak.name`, `adbreak.position`, `adbreak.starttime` | adBreakStart; existing ad/break cleanup |
| AdBreakComplete | `adbreakcomplete` | none | ad skip if active; adBreakComplete; playback flush |
| AdStart | `adstart` | `ad.name`, `ad.id`, `ad.position`, `ad.length` | adStart; playback flush; implicit preroll play |
| AdComplete | `adcomplete` | none | adComplete |
| AdSkip | `adskip` | none | adSkip |
| ChapterStart | `chapterstart` | `chapter.name`, `chapter.position`, `chapter.length`, `chapter.starttime` | chapterStart; previous chapter skip |
| ChapterComplete | `chaptercomplete` | none | chapterComplete |
| ChapterSkip | `chapterskip` | none | chapterSkip |
| SeekStart | `seekstart` | none | pauseStart via playback state; no seekStart endpoint |
| SeekComplete | `seekcomplete` | none | restored playback, or pauseStart from Init |
| BufferStart | `bufferstart` | none | bufferStart |
| BufferComplete | `buffercomplete` | none | restored playback, or pauseStart from Init |
| BitrateChange | `bitratechange` | none; uses **cached** QoE, not info | bitrateChange |
| StateStart | `statestart` | `state.name`: String | statesUpdate.statesStart |
| StateEnd | `stateend` | `state.name`: String | statesUpdate.statesEnd |

Source mapping: `M/MediaTrackerEventGenerator.java:155-196,306-355`; `E/internal/MediaTrackerEventGenerator.java:95-139,258-306`; string constants `M/MediaTrackerEventGenerator.java:38-80`, `EI/MediaInternalConstants.java:131-176`. All 15 explicitly declared Media.Event members occur above. `MediaType` contains Video/Audio; compiler-generated enum methods are excluded.

The following are complete, sanitized **session** and **ad/chapter/QoE/state** data variants; numeric types are governed by the preceding type table, not by JSON's undifferentiated number syntax:

```json
{
  "sessionstart": {
    "event.param": {
      "media.name": "<name>", "media.id": "<media-id>", "media.length": 60,
      "media.streamtype": "vod", "media.type": "video",
      "media.resumed": false, "media.prerollwaitingtime": 250
    },
    "event.metadata": {"<context-key>": "<context-value>"}
  },
  "adbreakstart": {"event.param": {"adbreak.name": "<break-name>", "adbreak.position": 1, "adbreak.starttime": 0}},
  "adstart": {"event.param": {"ad.name": "<ad-name>", "ad.id": "<ad-id>", "ad.position": 1, "ad.length": 10}, "event.metadata": {"<context-key>": "<context-value>"}},
  "chapterstart": {"event.param": {"chapter.name": "<chapter-name>", "chapter.position": 1, "chapter.length": 10, "chapter.starttime": 0}, "event.metadata": {"<context-key>": "<context-value>"}},
  "qoeupdate": {"event.param": {"qoe.bitrate": 1000, "qoe.startuptime": 0, "qoe.fps": 30, "qoe.droppedframes": 0}},
  "statestart": {"event.param": {"state.name": "<state-name>"}},
  "stateend": {"event.param": {"state.name": "<state-name>"}},
  "error": {"event.param": {"error.id": "<error-id>"}},
  "playheadupdate": {"event.param": {"time.playhead": 1}}
}
```

This block is a variant dictionary, not an eventData map containing keys such as `"sessionstart"`. Only its selected variant's fields are added to the shared envelope.

#### Rejection, timers, coalescing and local state

* A valid sessionStart while inactive replaces client session ID and starts a 750 ms timer. After **>500 ms** without API traffic, timer emits `playheadupdate` with `event.internal: true`, last playhead params if available, and no metadata. Before any playhead update, timer param may be absent.
* Complete/sessionEnd stop the timer. Invalid/repeated sessionStart still sends the direct EventHub request; it just does not create another active timer/session. A null Media.Event reaches the switch and can throw before dispatch.
* All non-start internal rules require active media. Invalid model maps, empty error IDs and invalid state transitions can reject downstream processing **after** direct EventHub publication.
* Play clears seek/buffer; Pause is rejected during seek/buffer. Playback-state changes are allowed only outside an ad break or while an ad is active.
* AdStart needs active break and valid different ad info; ad complete/skip needs active break/ad. New ad/break/chapter starts can skip an active old item; identical info is rejected. Chapter complete/skip requires active chapter.
* StateStart needs a valid inactive state, and fewer than ten distinct tracked states unless previously tracked; StateEnd needs an active matching state.
* Metadata null entries/invalid keys are cleaned internally. Preroll rules can defer execution; idle/24-hour rules can end/restart sessions. Therefore calls and backend hits are **not one-to-one**. Error paths log/drop; tracker `void` methods have no fail/success callback.
* QoE updates and playhead updates are **not NO_EVENT**: each dispatches its direct tracking request, even though its main downstream job is local context mutation.

Evidence: `M/MediaTrackerEventGenerator.java:210-296`; `E/internal/MediaTrackerEventGenerator.java:153-248`; `MI/MediaCollectionTracker.java:681-719,722-979,982-1024`; `EI/MediaEventTracker.java:231-349,431-508,719-1003`.

#### M.SESSION_CREATED — real-time only, conditional downstream

```json
{
  "eventName": "Media::SessionCreated",
  "eventType": "com.adobe.eventType.media",
  "eventSource": "com.adobe.eventsource.media.sessioncreated",
  "eventData": {
    "sessionid": "<client-session-uuid>",
    "mediaservice.sessionid": "<backend-session-id-or-null>"
  }
}
```

`MI/MediaSession.java:205-268` publishes only after a real-time sessionStart HTTP response is 2xx and has a Location header; malformed Location extraction can still yield null. `MI/MediaSessionCreatedDispatcher.java:28-44` is the actual builder, with no parent/response link. Downloaded-content upload does not implement this dispatch (`MI/MediaOfflineService.java:402`). Classic HTTP hit payloads are **not** Edge EventHub events and are not mislabeled as such here.

#### Public Media constants and metadata (non-callable surface)

`MediaConstants` nested containers have private constructors. `EXTENSION` is a registration class field, not a callable API.

* StreamType in both: `VOD=vod`, `LIVE=live`, `LINEAR=linear`, `PODCAST=podcast`, `AUDIOBOOK=audiobook`, `AOD=aod`.
* Classic `Config`: `CHANNEL=config.channel`, `DOWNLOADED_CONTENT=config.downloadedcontent`.
* Edge `TrackerConfig`: `CHANNEL=config.channel`, `AD_PING_INTERVAL=config.adpinginterval`, `MAIN_PING_INTERVAL=config.mainpinginterval`.
* Both `MediaObjectKey`: `RESUMED=media.resumed`, `PREROLL_TRACKING_WAITING_TIME=media.prerollwaitingtime`, `GRANULAR_AD_TRACKING=media.granularadtracking`.
* Classic PlayerState: `FULLSCREEN=fullscreen`, `PICTURE_IN_PICTURE=pictureInPicture`, `CLOSED_CAPTION=closedCaptioning`, `IN_FOCUS=inFocus`, `MUTE=mute`.
* Edge PlayerState: `FULLSCREEN=fullScreen`, `PICTURE_IN_PICTURE=pictureInPicture`, `CLOSED_CAPTION=closeCaption`, `IN_FOCUS=inFocus`, `MUTE=mute`. Do **not** copy classic casing/spelling into Edge state constants.

| Metadata constant member | Classic key | Edge input key → XDM sessionDetails field |
|---|---|---|
| Audio.ARTIST | `a.media.artist` | artist → artist |
| Audio.ALBUM | `a.media.album` | album → album |
| Audio.LABEL | `a.media.label` | label → label |
| Audio.AUTHOR | `a.media.author` | author → author |
| Audio.STATION | `a.media.station` | station → station |
| Audio.PUBLISHER | `a.media.publisher` | publisher → publisher |
| Video.SHOW | `a.media.show` | show → show |
| Video.SEASON | `a.media.season` | season → season |
| Video.EPISODE | `a.media.episode` | episode → episode |
| Video.ASSET_ID | `a.media.asset` | assetID → assetID |
| Video.GENRE | `a.media.genre` | genre → genre |
| Video.FIRST_AIR_DATE | `a.media.airDate` | firstAirDate → firstAirDate |
| Video.FIRST_DIGITAL_DATE | `a.media.digitalDate` | firstDigitalDate → firstDigitalDate |
| Video.RATING | `a.media.rating` | rating → rating |
| Video.ORIGINATOR | `a.media.originator` | originator → originator |
| Video.NETWORK | `a.media.network` | network → network |
| Video.SHOW_TYPE | `a.media.type` | showType → showType |
| Video.AD_LOAD | `a.media.adLoad` | adLoad → adLoad |
| Video.MVPD | `a.media.pass.mvpd` | mvpd → mvpd |
| Video.AUTHORIZED | `a.media.pass.auth` | isAuthenticated → **authorized** |
| Video.DAY_PART | `a.media.dayPart` | dayPart → dayPart |
| Video.FEED | `a.media.feed` | feed → feed |
| Video.STREAM_FORMAT | `a.media.format` | streamFormat → streamFormat |
| Ad.ADVERTISER | `a.media.ad.advertiser` | advertiser → advertisingDetails.advertiser |
| Ad.CAMPAIGN_ID | `a.media.ad.campaign` | campaignID → advertisingDetails.campaignID |
| Ad.CREATIVE_ID | `a.media.ad.creative` | creativeID → advertisingDetails.creativeID |
| Ad.PLACEMENT_ID | `a.media.ad.placement` | placementID → advertisingDetails.placementID |
| Ad.SITE_ID | `a.media.ad.site` | siteID → advertisingDetails.siteID |
| Ad.CREATIVE_URL | `a.media.ad.creativeURL` | creativeURL → advertisingDetails.creativeURL |

Here Audio/Video/Ad abbreviate `AudioMetadataKeys`/`VideoMetadataKeys`/`AdMetadataKeys`. All context values and these XDM metadata fields are **String**, including authentication. Unknown cleaned session/ad metadata becomes sorted `customMetadata: [{"name":"<key>","value":"<value>"}]`; chapter metadata is all custom metadata. Evidence: `M/MediaConstants.java:18-115`; `E/MediaConstants.java:20-117`; `EI/MediaXDMEventHelper.kt:31-245`.

### E.XDM — downstream Edge Media events

The publisher is **Edge Media**, while EventHub type is **Edge**. The Edge SDK subsequently sends the network request. `EI/MediaRealTimeSession.kt:180-195` uses the resolved name `"Edge Media - media.<suffix>"`; it does not forward the direct tracker event name.

```json
{
  "eventName": "Edge Media - media.sessionStart",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "media.sessionStart",
      "timestamp": "<ISO8601 UTC milliseconds>",
      "mediaCollection": {
        "playhead": 0,
        "sessionDetails": {
          "contentType": "vod", "friendlyName": "<name>", "hasResume": false,
          "length": 60, "name": "<media-id>", "streamType": "video",
          "channel": "<configured-channel>", "playerName": "<configured-player>"
        }
      }
    },
    "request": {"path": "/va/v1/sessionStart"}
  }
}
```

SessionDetails maps `media.id→name`, `media.name→friendlyName`, `media.streamtype→contentType`, `media.type→streamType`; these are easy to reverse accidentally. `hasResume` also becomes true for forced session restart. `appVersion` is optional configured String. Config keys are `edgeMedia.channel`, `edgeMedia.playerName`, `edgeMedia.appVersion`; tracker channel overrides configured channel.

Every non-start event uses this concrete common shape:

```json
{
  "eventName": "Edge Media - media.play",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "media.play",
      "timestamp": "<ISO8601 UTC milliseconds>",
      "mediaCollection": {"playhead": 1, "sessionID": "<backend-session-id>"}
    },
    "request": {"path": "/va/v1/play"}
  }
}
```

The table gives **every exact suffix** and additional `mediaCollection` fields. Replace `play` in all three locations (eventName, xdm.eventType suffix, request.path suffix) with the table literal. Complete expanded payloads, including separate state-start/end examples, are in manifest profile E.XDM.

| Exact suffix (`media.` prefix in name/XDM) | Additional fields beyond playhead/sessionID |
|---|---|
| `sessionStart` | sessionDetails as above; **omit sessionID** |
| `sessionComplete` | none |
| `sessionEnd` | none |
| `play` | none |
| `pauseStart` | none |
| `ping` | none |
| `error` | `errorDetails: {"name":"<error-id>","source":"player"}` |
| `bufferStart` | none |
| `bitrateChange` | current valid `qoeDataDetails`, if available |
| `adBreakStart` | `advertisingPodDetails: {"friendlyName":"<break-name>","index":1,"offset":0}` |
| `adBreakComplete` | none (also used for skipped break) |
| `adStart` | `advertisingDetails: {"friendlyName":"<ad-name>","length":10,"name":"<ad-id>","podPosition":1,"playerName":"<configured-player>"}` |
| `adSkip` | none |
| `adComplete` | none |
| `chapterSkip` | none |
| `chapterStart` | `chapterDetails: {"friendlyName":"<chapter-name>","index":1,"length":10,"offset":0}` |
| `chapterComplete` | none |
| `statesUpdate` (start) | `statesStart: [{"name":"<state-name>"}]` |
| `statesUpdate` (end) | `statesEnd: [{"name":"<state-name>"}]` |

All playhead/length/index/offset/podPosition and QoE fields are **Integer**. Names/config/metadata fields are String, hasResume Boolean. QoE attachment is `{"bitrate":1000,"droppedFrames":0,"framesPerSecond":30,"timeToStart":0}`; it is optional on **any** generated event when changed, and forced for bitrateChange when valid. There is no separate XDM `qoeupdate`, `seekstart`, `seekcomplete`, `buffercomplete` or public direct `pause` endpoint.

`MediaXDMEventGenerator.processPlayback` coalesces unchanged states; interval expiry generates ping. Buffer wins over seek, which maps to pause; play/pause follow. Flush occurs after AdStart/AdBreakComplete. Session complete/end first performs active ad/break/chapter cleanup. Changed QoE may be attached even on a generated playback/ping unrelated to a dedicated QoE event.

`MediaRealTimeSession` requires valid configuration. SessionStart can send without backend session ID; later hits queue until the matching Edge session response arrives. Invalid backend ID or matching status 400/type `https://ns.adobe.com/aep/errors/va-edge-0400-400` aborts; identity reset clears trackers/sessions. No callback or HTTP result is exposed by tracker methods.

Evidence: `EI/MediaXDMEventGenerator.kt:46-200,225-305`; `EI/MediaRealTimeSession.kt:93-107,117-131,137-195`; `EI/MediaInternalConstants.java:26-45`; `EI/xdm/XDMMediaEventType.kt:14-39`; `EI/xdm/XDMMediaEvent.kt:19-27`; `EI/xdm/XDMMediaSchema.kt:25-34`; `EI/xdm/XDMMediaCollection.kt:14-77`; nested `EI/xdm/XDMSessionDetails.kt:14-195`, `XDMQoeDataDetails.kt:14-44`, `XDMAdvertisingDetails.kt:14-75`, `XDMAdvertisingPodDetails.kt:14-35`, `XDMChapterDetails.kt:14-40`, `XDMPlayerStateData.kt:14-23`.

### Optimize request, model and callback contracts

#### End-to-end flow

```text
updatePropositions overloads [O02–O04]
  → validate/filter DecisionScopes → O.UPDATE → OptimizeExtension
  → query/xdm/data/request/sendCompletion shaping → O.UPDATE_EDGE
  → Edge SDK/backend
  ← attributed personalization:decisions / errors [N.RESPONSES]
  → valid offers accumulated → O.NOTIFICATION → registered O07 callback
  ← Edge completion [N.RESPONSES] or timeout
  → O.RESPONSE → update callback
  → O.COMPLETE → update cache/remove absent requested scopes/resume queue

getPropositions overloads [O05–O06]
  → validate → O.GET → immediate cached handling OR serialized queue
  → preview cache if nonempty, otherwise normal cache
  → O.RESPONSE → callback map (may be empty); NO network fetch

Offer.displayed/tapped / OfferUtils List<Offer>.displayed
  → local XDM generation → XDMUtils.trackWithData
  → O.DISPLAY/O.TAP Optimize request
  → extension adds optional configured datasetId → Edge interaction request → network

clearCachedPropositions [O08] → O.CLEAR → clear normal and preview caches; no response
onPropositionsUpdate [O07] → register permanent listener only; NO direct publication
```

Evidence: `O/Optimize.java:62-269,283-486`; `O/OptimizeExtension.java:241-558,569-718,839-995`; `O/Offer.java:283-378`; `O/OfferUtils.kt:25-86`; `O/XDMUtils.kt:31-85`.

#### O.UPDATE / O.UPDATE_EDGE

```json
[
  {
    "eventName": "Optimize Update Propositions Request",
    "eventType": "com.adobe.eventType.optimize",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "requesttype": "updatepropositions",
      "decisionscopes": [{"name": "<scope-name-or-base64>"}],
      "timeout": "<Long milliseconds>",
      "xdm": {"<caller-xdm-key>": "<safe-value>"},
      "data": {"<caller-data-key>": "<safe-value>"}
    }
  },
  {
    "eventName": "Edge Optimize Personalization Request",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "query": {"personalization": {
        "schemas": [
          "https://ns.adobe.com/personalization/html-content-item",
          "https://ns.adobe.com/personalization/json-content-item",
          "https://ns.adobe.com/personalization/default-content-item",
          "https://ns.adobe.com/experience/offer-management/content-component-html",
          "https://ns.adobe.com/experience/offer-management/content-component-json",
          "https://ns.adobe.com/experience/offer-management/content-component-imagelink",
          "https://ns.adobe.com/experience/offer-management/content-component-text"
        ],
        "decisionScopes": ["<scope-name-or-base64>"]
      }},
      "xdm": {"eventType": "decisioning.propositionFetch", "<caller-xdm-key>": "<safe-value>"},
      "data": {"<caller-data-key>": "<safe-value>"},
      "request": {"sendCompletion": true},
      "datasetId": "<configured-dataset-id>"
    }
  }
]
```

`requesttype`, `decisionscopes`, `timeout` are exact lowercase direct keys; downstream `decisionScopes` uses camelCase. Timeout is `(long)(seconds*1000)`. Empty xdm/data is omitted. No-timeout update uses the Long.MAX_VALUE sentinel (via double conversion); extension uses configured `optimize.timeout` seconds or a 10-second default. Explicit timeout overload passes supplied seconds. Empty/invalid scope collections are rejected before publication. Null scope elements are not guarded and can throw. The extension requires configuration and valid scopes; malformed data/config exceptions can be logged without a response, eventually causing timeout.

Downstream XDM forcibly overwrites `eventType` with `decisioning.propositionFetch`. `request.sendCompletion: true` is fixed. `datasetId` appears only for nonempty `optimize.datasetId`; free-form data only if nonempty. Parent is the originating Optimize update request, not the last unrelated EventHub event.

Evidence: `O/Optimize.java:128-199`; `O/ConfigUtils.kt:20-35`; `O/OptimizeExtension.java:357-464`; supported schemas list `O/OptimizeExtension.java:86-100`; names/namespaces/keys `O/OptimizeConstants.java:38-96,170-195`.

#### O.GET / O.CLEAR / O.LISTEN

```json
[
  {
    "eventName": "Optimize Get Propositions Request",
    "eventType": "com.adobe.eventType.optimize",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {"requesttype": "getpropositions", "decisionscopes": [{"name": "<scope-name-or-base64>"}]}
  },
  {
    "eventName": "Optimize Clear Propositions Request",
    "eventType": "com.adobe.eventType.optimize",
    "eventSource": "com.adobe.eventSource.requestReset",
    "eventData": null
  }
]
```

Get callback default timeout is 10 seconds. Custom timeout only affects callback registration, **not** eventData. Get is local-cache retrieval but is still an EventHub API. Fully cached scopes with none in progress are handled immediately; others are queued. Nonempty preview results supersede normal-cache results as a set; an empty propositions list is permitted. Clear attaches no data (null, not fabricated `{}`), clears normal and preview caches, and has no callback/response/network fetch.

`onPropositionsUpdate` registers a permanent listener for Optimize/notification; emits **nothing** itself. It reconstructs a `Map<DecisionScope,OptimizeProposition>`, invokes customer callback only when nonempty, ignores malformed/empty events, and ignores listener fail. Notification producers are described separately below. `O/Optimize.java:283-486`; `O/OptimizeExtension.java:259-334,839-995`.

#### O.RESPONSE / O.NOTIFICATION / O.COMPLETE

Success response template:

```json
{
  "eventName": "Optimize Response",
  "eventType": "com.adobe.eventType.optimize",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "propositions": [{
      "id": "<proposition-id>", "scope": "<scope-name>",
      "scopeDetails": {}, "activity": {}, "placement": {},
      "items": [{
        "id": "<offer-id>", "etag": "<etag>", "score": 0.0,
        "schema": "<schema-identifier>", "meta": {},
        "data": {
          "id": "<offer-id>", "type": "text/plain", "content": "<safe-content>",
          "language": ["<language>"], "characteristics": {"<key>": "<value>"}
        }
      }]
    }]
  }
}
```

Items contain String IDs/etag/schema/content/type, Double score, Map meta, List&lt;String&gt; language and Map&lt;String,String&gt; characteristics; some nullable properties can serialize as null. Propositions serialize activity/placement/scopeDetails maps even when empty. `propositions: []` is valid for response, not proof of a returned offer.

**O.NOTIFICATION:** same proposition serialization but exact envelope:

```json
{
  "eventName": "Optimize Notification",
  "eventType": "com.adobe.eventType.optimize",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "propositions": [{
      "id": "<proposition-id>", "scope": "<scope-name>", "scopeDetails": {}, "activity": {}, "placement": {},
      "items": [{
        "id": "<offer-id>", "etag": "<etag>", "score": 0.0, "schema": "<schema-identifier>", "meta": {},
        "data": {"id": "<offer-id>", "type": "text/plain", "content": "<safe-content>", "language": [], "characteristics": {}}
      }]
    }]
  }
}
```

Only matching tracked `personalization:decisions` with propositions containing valid offers produces the normal notification. Debug-preview handling is an independent producer, **not an unconditional consequence of update**. Notification does not chain to its triggering Edge response.

**O.COMPLETE:**

```json
{
  "eventName": "Optimize Update Propositions Complete",
  "eventType": "com.adobe.eventType.optimize",
  "eventSource": "com.adobe.eventSource.contentComplete",
  "eventData": {"completedUpdateRequestForEventId": "<edge-request-event-id>"}
}
```

After a valid Edge completion callback, Optimize dispatches response and complete, then caches accumulated propositions, removes requested scopes with no returned propositions, clears in-progress state and resumes the serial queue. This can occur with zero offers or a recorded backend error; **complete is not business success**.

Error variants of `Optimize Response` retain its name/type/source:

| Path | Exact data structure | Callback effect |
|---|---|---|
| get error | `{"responseerror":"<Integer AdobeError code>"}` | `AdobeCallbackWithError.fail` |
| update completion with stored backend error | `{"responseerror":{"type":"<error-type-or-null>","status":408,"title":"<title>","detail":"<detail>","report":null,"adobeError":{"errorName":"<name>","errorCode":"<Integer code>"}},"propositions":[]}` | typed `AdobeCallbackWithOptimizeError.fail`; callback code may then also call success |
| separate update timeout/failure helper with inspected Core sanitizer | `{}` after unsupported custom object is dropped | update callback sees empty data → typed unexpected error |

The last row is a real source distinction: `O/OptimizeExtension.java:1166-1177` puts an **AEPOptimizeError object**, not `error.toEventData()`, in `responseerror`. `C/Event.java:130-141` and `C/util/EventDataUtils.java:149-205` skip unsupported custom objects. Do not document that helper as emitting an error map. The actual dependency binary was not resolved, so the `{}` consequence is explicitly conditional on the supporting Core semantics.

Update callback code does not return after seeing `responseerror` before parsing propositions. When both fields exist it may invoke **fail and call**; plain AdobeCallback has no failure method. Source: `O/Optimize.java:213-269`. Get's error branch does return.

Other evidence: builders `O/OptimizeExtension.java:483-547,707-718,888-896,1153-1177`; cache completion `:569-635`; debug notification `:1005-1078`; serialization `O/OptimizeProposition.java:230-244`, `O/Offer.java:506-528`.

#### O.DISPLAY / O.TAP — offer behavior and list display

```json
[
  {
    "eventName": "Optimize Track Propositions Request",
    "eventType": "com.adobe.eventType.optimize",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "requesttype": "trackpropositions",
      "propositioninteractions": {
        "eventType": "decisioning.propositionDisplay",
        "_experience": {"decisioning": {"propositions": [{
          "id": "<proposition-id>", "scope": "<scope-name>", "scopeDetails": {},
          "items": [{"id": "<offer-id>"}]
        }]}}
      }
    }
  },
  {
    "eventName": "Edge Optimize Proposition Interaction Request",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.requestContent",
    "eventData": {
      "xdm": {
        "eventType": "decisioning.propositionDisplay",
        "_experience": {"decisioning": {"propositions": [{
          "id": "<proposition-id>", "scope": "<scope-name>", "scopeDetails": {},
          "items": [{"id": "<offer-id>"}]
        }]}}
      },
      "datasetId": "<configured-dataset-id>"
    }
  }
]
```

Tap uses the **same two complete envelopes and key structure**, changing only XDM `eventType` to exact literal `decisioning.propositionInteract`; both expanded examples are present in manifest O.TAP. Dataset override is optional, configuration required, and no parent/response links are set.

Single-offer displayed/tapped requires a live proposition reference. A missing/collected reference produces null XDM, and `trackWithData` dispatches nothing. These APIs do not render/navigate and have no completion callback.

List `displayed()` deduplicates propositions by proposition ID, includes only offers whose IDs appear in the input, and deduplicates those offer IDs. Empty list returns without dispatch. List helpers call `Offer.getProposition()` without guarding its nullable SoftReference/result: a standalone builder-created or collected offer can throw before dispatch. Do not infer the graceful single-offer null check applies to list helpers.

Nonempty `scopeDetails` is passed as-is. If empty, XDM generation creates scopeDetails from nonempty `activity` and `placement` maps (or `{}` when neither exists). `generateDisplayInteractionXdm`, `generateTapInteractionXdm`, and list `generateDisplayInteractionXdm` **only return data**; they do not call `trackWithData`.

Evidence: `O/Offer.java:273-378`; `O/OfferUtils.kt:25-86`; actual producer `O/XDMUtils.kt:31-85`; downstream builder `O/OptimizeExtension.java:920-981`; constants `O/OptimizeConstants.java:172-174`.

#### O.LOCAL — constructor/builder/model/error semantics

* **DecisionScope:** name constructor substitutes empty string for null. Activity/placement constructors Base64-encode `{"activityId":"<id>","placementId":"<id>"}`; when itemCount >1 add integer `itemCount`. Default count is 1. Invalid inputs give empty scope name; facade filtering later rejects it. Package-private validator accepts nonempty plain Target scope names and recognized encoded forms; malformed decoded JSON can fall through as a plain scope. Source `O/DecisionScope.java:39-77,85-231,246-267`.
* **OptimizeProposition:** public `fromEventData` validates required nonempty id/scope, parses items via Offer conversion, returns null on invalid data/exception, and sets offer-to-proposition soft references. Constructor and `toEventData` are package-private, **not public overloads**. Getters and explicit equals/hashCode publish nothing. `generateReferenceXdm()` returns `{"_experience":{"decisioning":{"propositionID":"<proposition-id>"}}}` with **no eventType**, and does not dispatch (`O/OptimizeProposition.java:45-65,129-217`).
* **Offer.Builder:** initializes required id/type/content with empty-string/UNKNOWN fallbacks, optional fields with empty values. Mutators/build are one-shot: any later setter or second build throws `UnsupportedOperationException`. No builder method emits events. `getProposition()` simply dereferences the reference and can throw if it was never assigned (`O/Offer.java:63-180,273-274`).
* **OfferType:** explicit `from` is case-insensitive; MIME mappings are JSON→`application/json`, TEXT→`text/plain`, HTML→`text/html`, IMAGE→`image/*`; null/unrecognized is UNKNOWN→`""`. Enum members themselves are constants (`O/OfferType.java:15-69`).
* **AEPOptimizeError:** public Kotlin data class with nullable String type/title/detail, nullable Int status, mutable nullable report/adobeError. Init derives AdobeError if absent. Timeout status 408→CALLBACK_TIMEOUT; 429/500/503→SERVER_ERROR; 502/504→NETWORK_ERROR; other/null→UNEXPECTED_ERROR. Local invalid-request factory explicitly sets INVALID_REQUEST; unexpected factory sets unexpected. `toEventData` returns type/status/title/detail/report/adobeError, nested AdobeError map has errorName/errorCode. Conversion methods use casts and may throw on malformed maps; `toAdobeError` looks at **status**, not serialized errorCode. No promise of round-trip preservation is made. `O/AEPOptimizeError.kt:26-132`; fixed titles/statuses `O/OptimizeConstants.java:199-240`.
* **O.CALLBACK:** `AdobeCallbackWithOptimizeError<T>` extends Core AdobeCallback&lt;T&gt;: customer implements `call(T)` and `fail(AEPOptimizeError)`. Update uses this typed fail interface; get uses Core AdobeCallbackWithError&lt;Map&lt;DecisionScope,OptimizeProposition&gt;&gt;. A plain AdobeCallback does not receive failure. `O/AdobeCallbackWithOptimizeError.java:16-17`, `O/Optimize.java:496-519`.

### N.RESPONSES — verified external Edge response builders

These are **Edge-owned publications**, not APIs/events newly owned by Optimize or Edge Media.

```json
[
  {
    "eventName": "AEP Response Event Handle",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "media-analytics:new-session",
    "eventData": {
      "type": "media-analytics:new-session",
      "requestId": "<http-batch-request-id>",
      "requestEventId": "<edge-session-start-event-id>",
      "payload": [{"sessionId": "<backend-session-id>"}]
    }
  },
  {
    "eventName": "AEP Response Event Handle",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "personalization:decisions",
    "eventData": {
      "type": "personalization:decisions",
      "requestId": "<http-batch-request-id>",
      "requestEventId": "<edge-request-event-id>",
      "payload": [{
        "id": "<proposition-id>", "scope": "<scope-name>", "scopeDetails": {},
        "items": [{"id": "<offer-id>", "schema": "https://ns.adobe.com/personalization/default-content-item", "meta": {}}]
      }]
    }
  },
  {
    "eventName": "AEP Response Complete",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.contentComplete",
    "eventData": {"requestId": "<http-batch-request-id>"}
  },
  {
    "eventName": "AEP Error Response",
    "eventType": "com.adobe.eventType.edge",
    "eventSource": "com.adobe.eventSource.errorResponseContent",
    "eventData": {
      "requestId": "<http-batch-request-id>", "requestEventId": "<edge-request-event-id>",
      "type": "<backend-error-type>", "status": 400, "title": "<safe-title>", "detail": "<safe-detail>", "report": {}
    }
  }
]
```

Edge response `payload` is caller/backend-defined data beyond verified consumed keys, not a fixed always-present proposition/offer schema. `EdgeEventHandle.toMap` serializes type/payload, **not eventIndex**; attribution adds requestId and, if available, requestEventId. Attributed handles/errors chain parent ID to Edge request ID but do not set responseId. Completion only occurs if request requested `sendCompletion`, uses `inResponseToEvent`, and carries requestId **without requestEventId**.

Optimize resolves request ID from parentId first, then data.requestEventId. Edge Media handler specifically reads data.requestEventId and the first payload entry's sessionId. Neither should be rewritten to use the other SDK's correlation implementation.

Evidence: actual builders `N/NetworkResponseHandler.java:310-329,605-664,676-707,754-784,900-932`; serialization `N/EdgeEventHandle.java:76-87`; literal names/keys `N/EdgeConstants.java:25-27,72-74`; consumers `O/OptimizeUtils.java:209-218`, `O/OptimizeExtension.java:647-718,738-824`, `EI/MediaExtension.java:103-144`. Stream/handle completion does not prove a proposition/display/media session was successful.

### Completeness, catalog eligibility and genuine gaps

| Repository | Inventoried | Documented | Reconciliation |
|---|---:|---:|---|
| Media | 20 | 20 | 11 facade/factory/version + all 9 tracker signatures |
| Edge Media | 18 | 18 | 9 facade/factory/version + all 9 tracker signatures |
| Optimize | 68 | 68 | 51 explicit Java callables + 16 Kotlin callables/properties + 1 inherited callback contract |
| **Total** | **106** | **106** | Every ID/signature above maps to a manifest profile |

The manifest has **21 profiles**, **86 payload examples** (including finite dynamic variants), both sets of 23 tracker names, 18 unique Edge Media downstream XDM event types (19 examples for start/end state variants), and all direct/conditional Optimize builders reviewed above. Source-facing methods were enumerated rather than inferred from a README. Public field/enum constants are separately documented but not counted as callable APIs.

**Eligible:** direct tracker/facade EventHub requests; state-dependent Edge Media XDM; classic conditional session-created; Optimize requests/responses/notifications/completion/interaction children; attributed external Edge responses. Classification is conditional where appropriate, not an unconditional chain.

**Not eligible as emitted events:** NO_EVENT factories/getters/model conversion/XDM generators, CALLBACK_ONLY declarations/listener registration, raw classic HTTP hit JSON, class-registration fields, compiler-generated methods, or unrelated rules/listeners that might independently react.

**Explicit exclusions:** extension-author infrastructure/lifecycle subclasses, concrete internal tracker implementation, public methods on internal MediaObject classes, internal XDM/ConfigUtils/XDMUtils, protected/package-private helpers, tests/samples/build/generated output, generic inherited Object/Enum methods, synthetic enum/data-class/JVM default bridges. OptimizeProposition's package-private constructor and Offer's package-private parser are not invented as customer API constructors. AEPOptimizeError's six Kotlin property declarations (including two vars) represent their generated accessor surface without duplicating compiler methods.

**Genuine limitations (not missing API inventory):**

1. These are telemetry-integration branches. Optional reflective TelemetryBridge hook calls occur in classic `createTracker(callback)`, both Edge Media createTracker overloads, and deprecated Optimize update. The helper is instrumentation infrastructure, not an extension customer facade. Plugin absent is a no-op; its runtime presence and external plugin-produced EventHub payload are not verified within these three repos, so none is invented. Evidence: classic `M/Media.java:56`, `E/Media.java:37-53`, `O/Optimize.java:67-69`, each module's `com/adobe/marketing/mobile/telemetrybridge/TelemetryBridge.java:1-35`.
2. Core/Edge supporting snapshots are source evidence, not proof of the installed binary/dependency resolution. Backend delivery, actual runtime data and optional plugin operation were not executed. The unsupported-custom-error response branch is documented conditionally, not repaired.

**Source-access gaps:** none for the three assigned repositories. No SDK production/configuration/Git-state edits, agents, workflows, builds, dependency installs, commits, pushes or wiki operations were used. Only `media-optimize.md` and `media-optimize.json` are documentation outputs; the parent's final Markdown was not changed.

---

<a id="family-places-new"></a>

## Places, Flags, legacy Offer Decisioning, and UserProfile — Android API/event evidence

**Summary:** 83 customer-facing callable entries are inventoried: Places 28, Flags 17, Offer 34, UserProfile 4. Flags and Offer both contain real implementations. The requested legacy Core UserProfile module is absent.

### Provenance and boundaries

Clone paths below are relative to the artifact root. All four clones were on `main`, with clean tracked source at initial inspection.

| Repository | HEAD | Evidence |
|---|---|---|
| `sources/aepsdk-places-android` | `f390d2fed3e393c87afef7bb358530eae4d2a2de` | **UNINDEXED** source reads. Scout initially reported not attached; one attachment succeeded, but retry reported BM25 unavailable. |
| `sources/aepsdk-flags-android` | `47be95ed4932fd14311acff13b2f1f05490ef45f` | Scout source reads succeeded after attachment; freshness not certified. |
| `sources/aepsdk-offer-android` | `4a1e53bae1ddf69b12e55aee7ada6eabc2e85d8b` | Scout source reads succeeded after attachment; freshness not certified. |
| `sources/aepsdk-userprofile-android` | `306bd0f33c9aa937116cbd27068c1493ba5b9eab` | Scout source reads succeeded after attachment; freshness not certified. |

Scout attachment can add untracked `.scout/` metadata (observed for Places); it is not an SDK source edit. No SDK production file, parent final document, build configuration, or Git commit was changed. No AGENTS.md was found in the initial clone inventory or checked ancestors. Declaration census, not README-only enumeration, establishes the inventories; no callable API baseline was discovered.

Supporting Core source is `adobe/aepsdk-core-android`, HEAD `3a2eb50e437742e9738222bcc7e9099c4af32481`, branch `poc/telemetry-integration`. Tracked source was clean; three existing untracked telemetry Markdown files were left untouched (listed in the manifest). It supplies modern `Event`, type/source constants, and shared-state notification semantics, **not** a substitute implementation of the old Offer dependency. Final verification found only untracked `.scout/` attachment metadata in each of the four clones, with no tracked source changes.

#### Reading conventions

* Every public callable row has a unique ID also present in `places-new.json`. `final` and nullability annotations are elided from signatures, not parameters or overloads.
* File aliases in the tables below expand to the exact paths listed at the start of each section; `alias:line` is a declaration reference within that repository. The JSON manifest carries complete paths per row.
* Public fields (`EXTENSION`, version constants, enum values) are not callable APIs. Compiler-generated enum `values`/`valueOf` and synthetic methods are excluded. Explicit `equals`, `hashCode`, and enum overrides are included.
* Core-owned `AdobeCallback<T>.call(T)` and `AdobeCallbackWithError<T>.fail(AdobeError)` are callback contracts, not separately published extension events. Typed results and failure paths are documented below. No new public callback interface is declared by these four app-facing surfaces.
* **NO_EVENT** means the reviewed body is local-only, not “this object can never later participate in an event.” In particular, constructing `OfferExperienceEvent` does not send anything.

#### Envelope/link semantics

Examples are **normalized illustrative EventHub payloads**, not literal runtime dumps or `sdk.usage` uploads. Angle-bracket values are safe typed placeholders, not real identifiers, attributes, configuration, tokens, or URLs. Optional keys are noted separately.

For modern Core, `code/core/src/main/java/com/adobe/marketing/mobile/Event.java:83-92,152-164,195-224` verifies:

```json
{
  "eventId": "<new-UUID-for-each-Event.Builder>",
  "parentId": null,
  "responseId": null,
  "timestamp": "<epoch-milliseconds-at-build>"
}
```

`inResponseToEvent(request)` sets **both** `parentId` and `responseId` to the request event's UUID. A correlated response is still a new event with its own UUID and timestamp. Shared-state version association is not the same thing as an event parent link. For every example without an explicit correlation override, the default null links apply.

**Version boundary:** old Offer calls `MobileCore.dispatchResponseEvent(response, request, errorCallback)`. Its response builder itself has no links. The reviewed source proves the association call, but the older Core helper implementation is not vendored here; its final `parentId`/`responseId` mutation remains unverified. Do not copy modern `inResponseToEvent` semantics onto it.

Full modern literals are verified in Core `code/core/src/main/java/com/adobe/marketing/mobile/EventType.java:34,42,49,55` and `EventSource.java:28-35`; Offer and Flags also define their own literals at the references below. Capitalization is significant, especially `userProfile` and `offerDecisioning`.

### Places 3.0.2

#### Public API inventory — 28 entries

Exact source aliases:

* **PA** = `code/places/src/phone/java/com/adobe/marketing/mobile/Places.java`
* **PP** = `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesPOI.java`
* **PU** = `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesUtil.java`
* **PS** = `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesAuthorizationStatus.java`
* **PE** = `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesRequestError.java`

`Places` is `com.adobe.marketing.mobile.Places`; models/utilities/enums are in `com.adobe.marketing.mobile.places`.

| ID | Class | Public signature | Profile(s) | Declaration |
|---|---|---|---|---|
| P01 | Places | `static String extensionVersion()` | NO_EVENT | PA:55 |
| P02 | Places | `static void getNearbyPointsOfInterest(Location location, int limit, AdobeCallback<List<PlacesPOI>> successCallback, AdobeCallback<PlacesRequestError> errorCallback)` | PLACES_NEARBY, STATE_PLACES | PA:74 |
| P03 | Places | `static void processGeofenceEvent(GeofencingEvent geofencingEvent)` | PLACES_REGION, STATE_PLACES | PA:95 |
| P04 | Places | `static void processGeofence(Geofence geofence, int transitionType)` | PLACES_REGION, STATE_PLACES | PA:149 |
| P05 | Places | `static void getCurrentPointsOfInterest(AdobeCallback<List<PlacesPOI>> callback)` | PLACES_CURRENT | PA:193 |
| P06 | Places | `static void getLastKnownLocation(AdobeCallback<Location> callback)` | PLACES_LAST | PA:279 |
| P07 | Places | `static void clear()` | PLACES_CLEAR, STATE_PLACES | PA:373 |
| P08 | Places | `static void setAuthorizationStatus(PlacesAuthorizationStatus status)` | PLACES_AUTH, STATE_PLACES | PA:399 |
| P09 | PlacesPOI | `String getIdentifier()` | NO_EVENT | PP:37 |
| P10 | PlacesPOI | `String getName()` | NO_EVENT | PP:46 |
| P11 | PlacesPOI | `boolean containsUser()` | NO_EVENT | PP:55 |
| P12 | PlacesPOI | `void setUserIsWithin(boolean userIsWithin)` | NO_EVENT | PP:64 |
| P13 | PlacesPOI | `double getLatitude()` | NO_EVENT | PP:73 |
| P14 | PlacesPOI | `double getLongitude()` | NO_EVENT | PP:82 |
| P15 | PlacesPOI | `int getRadius()` | NO_EVENT | PP:91 |
| P16 | PlacesPOI | `Map<String,String> getMetadata()` | NO_EVENT | PP:100 |
| P17 | PlacesPOI | `int getWeight()` | NO_EVENT | PP:109 |
| P18 | PlacesPOI | `String getLibrary()` | NO_EVENT | PP:118 |
| P19 | PlacesPOI | `boolean equals(Object o)` | NO_EVENT | PP:228 |
| P20 | PlacesPOI | `boolean comparePriority(PlacesPOI otherPOI)` | NO_EVENT | PP:302 |
| P21 | PlacesPOI | `int hashCode()` | NO_EVENT | PP:315 |
| P22 | PlacesAuthorizationStatus | `String stringValue()` | NO_EVENT | PS:49 |
| P23 | PlacesRequestError | `int getValue()` | NO_EVENT | PE:69 |
| P24 | PlacesRequestError | `static PlacesRequestError fromInt(int value)` | NO_EVENT | PE:81 |
| P25 | PlacesUtil | `PlacesUtil()` — implicit public default constructor | NO_EVENT | PU:26 |
| P26 | PlacesUtil | `static boolean isValidLat(double latitude)` | NO_EVENT | PU:41 |
| P27 | PlacesUtil | `static boolean isValidLon(double longitude)` | NO_EVENT | PU:51 |
| P28 | PlacesUtil | `static List<PlacesPOI> convertMapToPOIList(List<Map> poiMap)` | NO_EVENT | PU:77 |

There is **no public PlacesPOI constructor or builder**, and no public `PlacesRegion` type in this revision. POI's protected constructors and setters are outside this public-only census, not silently mapped to customer event APIs. `PlacesUtil` is retained conservatively because it is public even though the facade also uses it internally.

#### NO_EVENT model/enum/utility behavior

P01 returns `"3.0.2"`. POI getters read fields; `setUserIsWithin` mutates this object's flag only, without persistence, shared state, or EventHub dispatch. Metadata is the field map, not a newly protected wrapper. `equals` compares POI values including membership/weight/library/metadata; `hashCode` computes locally. `comparePriority` returns true for null other, lower numeric weight, or equal weight with this radius no larger than the other's. It does not publish a region transition (`PP:228-329`).

Coordinate validators accept inclusive latitude `[-90,90]` and longitude `[-180,180]`. Conversion uses the POI map keys below with defaults and returns newly constructed POIs; no event or network request (`PU:41-95`).

Authorization enum wire values: `DENIED→denied`, `ALWAYS→always`, `UNKNOWN→unknown`, `RESTRICTED→restricted`, `WHEN_IN_USE→wheninuse` (`PS:18-50`).

Request-error integer mapping (`PE:15-88`): `OK=0`, `CONNECTIVITY_ERROR=1`, `SERVER_RESPONSE_ERROR=2`, `INVALID_LATLONG_ERROR=3`, `CONFIGURATION_ERROR=4`, `QUERY_SERVICE_UNAVAILABLE=5`, **both** `PRIVACY_OPTED_OUT=6` and `UNKNOWN_ERROR=6`. `fromInt(6)` returns the first matching constant, `PRIVACY_OPTED_OUT`; unmatched integers return `UNKNOWN_ERROR`. These duplicate values are not normalized away.

#### Shared POI eventData object schema

`code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesPOI.java:332-343` and `PlacesConstants.java:40-50` verify all keys:

```json
{
  "regionid": "<poi-id>",
  "regionname": "<poi-name>",
  "latitude": "<latitude:number>",
  "longitude": "<longitude:number>",
  "radius": "<radius:int>",
  "regionmetadata": {"<metadata-key>": "<metadata-value>"},
  "useriswithin": "<boolean>",
  "libraryid": "<library-id>",
  "weight": "<weight:int>"
}
```

Nullable metadata is possible in source; the safe example uses a map. All later POI schema references mean this object, not a string at runtime.

#### PLACES_NEARBY — P02

```text
Places.getNearbyPointsOfInterest
 → location fields + count → MobileCore.dispatchEventWithResponseCallback (5000 ms)
 → PlacesExtension readiness: Configuration shared state SET
 → save valid last-known location (unless opted out)
 → validate Places config/privacy → PlacesQueryService HTTP GET
 → parse POIs → update cached/persisted membership + shared state
 → correlated nearby response + independent nearby broadcast
 → successCallback(List<PlacesPOI>) or errorCallback(PlacesRequestError)
```

Source: `PA:503-589`; `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesExtension.java:105-120,147-162,265-298,348-408`; `PlacesQueryService.java:60-204`; `PlacesDispatcher.java:35-76`.

Direct event:

```json
{
  "eventName": "requestgetnearbyplaces",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "latitude": "<latitude:number>",
    "longitude": "<longitude:number>",
    "count": "<limit:int>",
    "requesttype": "requestgetnearbyplaces"
  }
}
```

Response template:

```json
{
  "eventName": "responsegetnearbyplaces",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "nearbypois": ["<POI object using shared schema above>"],
    "status": 0
  }
}
```

On success, this payload is emitted **twice**: one response with both links equal to the request UUID and one broadcast with both links null. On failure, a correlated response has `nearbypois: []` and numeric status; no success broadcast. Constant names resolve in `PlacesConstants.java:92-99,108-130` and `PA:592-643`.

The facade dereferences Location without a null guard and does not validate limit. Service URL assembly validates coordinates, then sends HTTPS GET to the configured endpoint's `placesedgequery` path with `latitude`, `longitude`, `limit`, and library query parameters. This is **not** an Edge EventHub event. Missing networking or invalid URL/coordinates maps to `INVALID_LATLONG_ERROR`; null connection/non-200 response maps to `CONNECTIVITY_ERROR`; empty/malformed server body maps to `SERVER_RESPONSE_ERROR` (`PlacesQueryService.java:60-204`). Invalid Places configuration and opted-out privacy return statuses 4 and 6 respectively before a network query. Status/callback parsing errors or callback timeout map to `UNKNOWN_ERROR`. Dispatch/response arrival alone is not business success.

#### PLACES_CURRENT — P05

```text
getCurrentPointsOfInterest → request → cached containsUser filter
 → correlated response + independent broadcast → callback
```

```json
{
  "eventName": "requestgetuserwithinplaces",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {"requesttype": "requestgetuserwithinplaces"}
}
```

```json
{
  "eventName": "responsegetuserwithinplaces",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {"userwithinpois": ["<POI object using shared schema above>"]}
}
```

This “get” is **not NO_EVENT** and does not fetch current GPS/server state. A null callback suppresses the request. Both responses occur even for an empty list. One is `inResponseToEvent(request)`; the broadcast is unlinked. Data parse problems return an empty list. Timeout calls `fail` when the callback supports errors; otherwise calls the ordinary callback with an empty list. Evidence: `PA:193-266`; `PlacesExtension.java:215-228`; `PlacesState.java:244-253`; `PlacesDispatcher.java:100-135`.

#### PLACES_LAST — P06

```text
getLastKnownLocation → request → persisted last-known coordinates
 → correlated response [+ broadcast only when location exists] → callback(Location or null)
```

```json
{
  "eventName": "requestgetlastknownlocation",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {"requesttype": "requestgetlastknownlocation"}
}
```

```json
{
  "eventName": "responsegetlastknownlocation",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "lastknownlatitude": "<latitude:number>",
    "lastknownlongitude": "<longitude:number>"
  }
}
```

Null callback suppresses dispatch. No saved location yields literal `999.999` for both coordinates and only the correlated response; facade detects invalid coordinates and calls back null. A valid saved location yields both correlated and unlinked responses. It is not a GPS query. Timeout uses `fail`, or null for a plain callback. The malformed-response `DataReaderException` catch directly dereferences `userCallbackWithError` without a null guard in this version; do not promise a safe error callback for every malformed response. Evidence: `PA:279-369`; `PlacesExtension.java:237-256`; `PlacesDispatcher.java:139-177`.

#### PLACES_REGION — P03 and P04, including all public geofence paths

```text
processGeofenceEvent(event) ── one request per non-null triggering Geofence ─┐
processGeofence(geofence, transitionType) ── at most one request ────────────┤
 → ENTER/EXIT mapping → requestprocessregionevent                           │
 → privacy gate → cached POI lookup → membership/current/last POI update   │
 → persist + shared state → responseprocessregionevent                    │
 → Location Tracking Event (Edge request)                                 ┘
```

Rejected paths: `GeofencingEvent.hasError()`, unsupported transitions (including DWELL), null/all-null triggering list, or null single Geofence cause zero dispatches. A null `GeofencingEvent` is not defensively guarded. P04 logs null Geofence but continues through list conversion, which skips it. Only ENTER→`entry` and EXIT→`exit` exist (`PA:95-182,432-490`).

Direct entry example:

```json
{
  "eventName": "requestprocessregionevent",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "regionid": "<geofence-request-id>",
    "regioneventtype": "entry",
    "requesttype": "requestprocessregionevent"
  }
}
```

Places response:

```json
{
  "eventName": "responseprocessregionevent",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "triggeringregion": "<POI object using shared schema above>",
    "regioneventtype": "entry",
    "timestamp": "<original-request-event.timestamp:epoch-ms>"
  }
}
```

Edge request:

```json
{
  "eventName": "Location Tracking Event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "location.entry",
      "placeContext": {
        "POIinteraction": {
          "poiDetail": {
            "poiID": "<poi-id>",
            "name": "<poi-name>",
            "metadata": {"list": [{"key": "<metadata-key>", "value": "<metadata-value>"}]}
          }
        }
      }
    }
  }
}
```

For EXIT, all event names/types/sources/key shapes remain the same; `regioneventtype` becomes literal `"exit"` and XDM eventType becomes `"location.exit"`. **Both downstream events have null parentId/responseId**; a name beginning with “response” does not prove a response link. Only the Places response's **eventData** timestamp copies the original request timestamp. Edge's event mask is exactly `["xdm.eventType","xdm.placeContext.POIinteraction.poiDetail.poiID"]`. It is an EventHistory mask, not a correlation field. No datasetId/meta is inserted by this dispatcher.

Processing requires a cached POI matching `regionid` and non-opted-out privacy. Entry sets membership true, last-entered, and priority-selected current POI. Exit sets membership false, recalculates current, and records last-exited. Both update membership validity and persist. There is no explicit same-transition deduplication in `processRegionEvent`; do not infer one.

Null region from unknown/empty ID makes the Places response helper return without dispatch. The extension then still calls the Edge helper, which dereferences its non-null parameter without a guard; that path is **not** a guaranteed no-throw/Edge-success path. Edge metadata construction also iterates `poi.getMetadata()` without a null guard.

Evidence: `PlacesState.java:118-193`; `PlacesExtension.java:411-441`; `PlacesRegion.java:32-65`; `PlacesDispatcher.java:79-97,187-283`; `PlacesConstants.java:165-208`, all under `code/places/src/main/java/com/adobe/marketing/mobile/places/`.

#### PLACES_CLEAR and PLACES_AUTH — P07, P08

```json
{
  "eventName": "requestreset",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {"requesttype": "requestreset"}
}
```

```json
{
  "eventName": "requestsetauthorizationstatus",
  "eventType": "com.adobe.eventType.places",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {"requesttype": "requestsetauthorizationstatus", "authstatus": "unknown"}
}
```

Clear → reset persisted/in-memory data → empty shared state. Authorization → validate/store status label → shared state; it **does not request OS permission**. Null authorization status suppresses dispatch. Other valid `authstatus` literals are `denied`, `always`, `restricted`, `wheninuse`. Neither API has a Places response callback or Places response event. Both can cause Core-owned STATE_PLACES notification. Evidence: `PA:373-421`; `PlacesExtension.java:306-336`.

### Flags — real customer implementation, not a template

`code/flags/src/main/java/com/adobe/marketing/mobile/flags/Flag.java` implements both evaluation APIs, and `FlagExtension.java:50-100,131-145` registers/routes them. `code/flags/build.gradle.kts:33-46,58-64` configures the actual library/publishing and generated version. This is not a fabricated `Flags` facade: the class is singular **Flag**. `README.md:36-50` documents installation, but no remote binary availability check was performed.

#### Public API inventory — 17 entries

All classes are in `com.adobe.marketing.mobile.flags`. Exact aliases:

* **FA** = `code/flags/src/main/java/com/adobe/marketing/mobile/flags/Flag.java`
* **FC** = `code/flags/src/main/java/com/adobe/marketing/mobile/flags/FeatureEvaluationContext.java`
* **FR** = `code/flags/src/main/java/com/adobe/marketing/mobile/flags/FeatureEvaluationResult.java`
* **FM** = `code/flags/src/main/java/com/adobe/marketing/mobile/flags/AnalyticsParam.java`

| ID | Class | Public signature | Profile(s) | Declaration |
|---|---|---|---|---|
| F01 | Flag | `static String extensionVersion()` | NO_EVENT | FA:54 |
| F02 | Flag | `static void getFeature(String featureKey, FeatureEvaluationContext evaluationContext, AdobeCallback<FeatureEvaluationResult> callback)` | FLAGS_GET, FLAGS_EXPOSURE, conditional STATE_FLAGS | FA:70 |
| F03 | Flag | `static void isFeatureEnabled(String featureKey, FeatureEvaluationContext evaluationContext, AdobeCallback<Boolean> callback)` | FLAGS_ENABLED, FLAGS_EXPOSURE, conditional STATE_FLAGS | FA:162 |
| F04 | FeatureEvaluationContext | `static Builder builder()` | NO_EVENT | FC:50 |
| F05 | FeatureEvaluationContext | `Map<String,List<String>> getAttributes()` | NO_EVENT | FC:59 |
| F06 | FeatureEvaluationContext.Builder | `Builder withAttributes(Map<String,List<String>> attributes)` | NO_EVENT | FC:94 |
| F07 | FeatureEvaluationContext.Builder | `FeatureEvaluationContext build()` | NO_EVENT | FC:109 |
| F08 | FeatureEvaluationResult | `FeatureEvaluationResult(int id, String key, String featureGroupKey, String meta, AnalyticsParam analyticsParam)` | NO_EVENT | FR:24 |
| F09 | FeatureEvaluationResult | `int getId()` | NO_EVENT | FR:37 |
| F10 | FeatureEvaluationResult | `String getKey()` | NO_EVENT | FR:41 |
| F11 | FeatureEvaluationResult | `String getFeatureGroupKey()` | NO_EVENT | FR:45 |
| F12 | FeatureEvaluationResult | `String getMeta()` | NO_EVENT | FR:49 |
| F13 | FeatureEvaluationResult | `AnalyticsParam getAnalyticsParam()` | NO_EVENT | FR:53 |
| F14 | AnalyticsParam | `AnalyticsParam(int featureGroupId, int featureId, String variantId)` | NO_EVENT | FM:22 |
| F15 | AnalyticsParam | `int getFeatureGroupId()` | NO_EVENT | FM:29 |
| F16 | AnalyticsParam | `int getFeatureId()` | NO_EVENT | FM:33 |
| F17 | AnalyticsParam | `String getVariantId()` | NO_EVENT | FM:37 |

NO_EVENT: F01 returns generated `BuildConfig.FLAGS_VERSION`, derived from `moduleVersion` with `-SNAPSHOT` removed (`code/flags/build.gradle.kts:58-64`). Models store/return fields. Builder uses a private constructor; `builder()` is the public construction path. `build` freezes/copies map and list values; empty map becomes null. `withAttributes` throws when `this.attributes != null` already, so repeated null calls are not rejected despite the stronger “at most once” comment (`FC:63-110`). None of these methods dispatches.

**Embedded engine boundary:** public Java classes under `code/flags/src/main/java/com/adobe/marketing/mobile/flags/engine/` are bundled implementation, not an additional extension customer facade. This includes `FlagClient`, engine `FeatureResult`/`AnalyticsParam`, configuration/request builders, caches, policies, and runtime filters. Do not conflate engine models with the same-named app model. `code/flags/README.md:9-19` identifies the app-facing API versus client internals; `build.gradle.kts:39-46` confirms engine source is bundled. These are explicitly excluded infrastructure, not claimed nonexistent. Similarly `internal/` public adapters/providers/fetchers are implementation infrastructure.

#### FLAGS_GET / FLAGS_ENABLED — F02, F03

```text
Flag API → context attributes → validation → dispatchEventWithResponseCallback (10000 ms)
 → FlagExtension config/initialization/Edge Identity readiness
 → FlagClientManager requesttype dispatch → engine FlagClient.getFeature
 → correlated Flags Response → typed caller callback
 → independently queue eligible exposure → later coalesced Edge event
```

Readiness/config evidence: `FlagExtension.java:78-100,131-145,167-186`; `internal/FlagConfigurationProvider.java:35-67,86-125`. Required nonempty values are `experienceCloud.org`, `flags.sandbox`, `flags.clientId`. Missing/blank `edge.domain` resolves to the code default `edge.adobedc.net`. When required config is absent, readiness allows processing to reach a not-ready error rather than holding forever; a valid config waits for initialization completion and identity readiness. Initialization resolves a pending shared state `ready`/`failed` (`FlagClientManager.java:91-145`), not a new state per successful evaluation.

Direct requests:

```json
{
  "eventName": "Flags Get Feature Request",
  "eventType": "com.adobe.eventType.flags",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "requesttype": "getfeature",
    "featurename": "<feature-key>",
    "context": {"<attribute-key>": ["<attribute-value>"]}
  }
}
```

```json
{
  "eventName": "Flags Is Feature Enabled Request",
  "eventType": "com.adobe.eventType.flags",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "requesttype": "isfeatureenabled",
    "featurename": "<feature-key>",
    "context": {"<attribute-key>": ["<attribute-value>"]}
  }
}
```

`context` is omitted when attributes are null. An empty feature key produces no request and reports `UNEXPECTED_ERROR` only through an error-capable callback. NonNull inputs are dereferenced, not defensively guarded; null evaluationContext is not an optional overload.

Served feature response:

```json
{
  "eventName": "Flags Response",
  "eventType": "com.adobe.eventType.flags",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "feature": {
      "id": "<feature-id:int>",
      "key": "<feature-key>",
      "featureGroupKey": "<optional-group-key>",
      "meta": "<optional-nonblank-metadata>",
      "analyticsParam": {
        "featureGroupId": "<group-id:int>",
        "featureId": "<feature-id:int>",
        "variantId": "<optional-variant-id>"
      }
    }
  }
}
```

Optional: `featureGroupKey` only if non-null; `meta` only if nonblank; `analyticsParam` only if present; its `variantId` only if non-null. A normal not-served result uses **`eventData: {}`** for F02 and calls `callback.call(null)`. F03's result schema is:

```json
{
  "eventName": "Flags Response",
  "eventType": "com.adobe.eventType.flags",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {"isenabled": "<boolean>"}
}
```

Error schema, shared by both APIs:

```json
{
  "eventName": "Flags Response",
  "eventType": "com.adobe.eventType.flags",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {"responseerror": "<AdobeError.getErrorCode():int>"}
}
```

All these response variants use `inResponseToEvent(request)`; **both** links point to the request UUID. The facade maps `responseerror` to `UNEXPECTED_ERROR`, rather than treating an error response as a false business outcome. Missing/malformed enabled key uses `DataReader.optBoolean(..., false)`; an empty/null enabled response fails. Timeout and parsing failures have no ordinary callback error channel when only `AdobeCallback` is supplied.

Both manager handlers call engine **`getFeature`**, then use `feature != null && key != null && !key.isEmpty()` to decide served/enabled. Unknown, not-targeted, and control outcomes need not be errors. Engine evaluation is local through `evaluator.evaluate` (`engine/FlagClient.java:164-170`); do not invent a separate Edge network request for every evaluation.

Evidence: `FA:70-148,162-246`; `FlagClientManager.java:176-195,222-449`; `internal/FlagConstants.kt:18-56`. All non-aliased implementation filenames in this section are under `code/flags/src/main/java/com/adobe/marketing/mobile/flags/`.

#### FLAGS_EXPOSURE — conditional, coalesced downstream of F02/F03

Eligibility is **not** equivalent to a true/enabled public result. Any non-null evaluated feature with non-null analytics and variantId qualifies, including control cohort results. Missing feature/analytics/variant skips exposure (`ExposureEventIdGenerator.java:36-110`; `FlagClientManager.java:382-400`).

Queue identity and payload correlation are distinct:

| Feature | Queue aggregation key | Edge scopeDetails.correlationID |
|---|---|---|
| Standalone | `F-{featureId}-{variantId}` | `F-{featureId}-{variantId}` |
| Group | `FG-{groupId}-{featureId}-{variantId}` | `FG-{groupId}-{variantId}` |

Standalone is selected by analytics featureGroupId `-1` **or** group key `"||features||"`. For repeated evaluations of a key, display count increments and the last-evaluated timestamp refreshes. Flush dispatches one event per aggregation key, not one per public API invocation. Distinct group features may therefore share payload correlationID while remaining separate events/items.

Executable flush defaults: constructor `FeatureExposureQueue.java:89-94` selects `FlagConstants.ExposureQueue` values **20 distinct keys / 150000 ms** (`internal/FlagConstants.kt:124-127`). `FeatureExposureQueue.java:149-205,228-258,286-395` implements inbound coalescing, threshold flush, one-shot timer and shutdown. `FlagClientManager.java:198-218` pauses timer and flushes on lifecycle pause, resumes on foreground. `FlagClientManager.java:62-70` wires flush to `FlagEdgeHandler.dispatchExposureEvents`.

Standalone normalized payload:

```json
{
  "eventName": "Flags Edge Feature Exposure",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "request": {"path": "/v1/collect"},
    "xdm": {
      "eventType": "decisioning.propositionDisplay",
      "timestamp": "<last-evaluated-ISO8601-UTC>",
      "_experience": {
        "decisioning": {
          "propositionEventType": {"display": "<positive-count:int>"},
          "propositions": [{
            "scopeDetails": {
              "decisionProvider": "FLAGS",
              "correlationID": "F-<feature-id>-<variant-id>",
              "activity": {"id": "F-<feature-id>", "name": "<analytics-feature-key>"},
              "experience": {"id": "Variant-<variant-id>"},
              "strategies": [{"algorithmID": "murmur"}],
              "characteristics": {"entityType": "feature"}
            }
          }]
        }
      }
    }
  }
}
```

Group uses the same eventName/type/source and outer keys; replace the propositions element with:

```json
{
  "scopeDetails": {
    "decisionProvider": "FLAGS",
    "correlationID": "FG-<group-id>-<variant-id>",
    "activity": {"id": "FG-<group-id>", "name": "<feature-group-key>"},
    "experience": {"id": "Variant-<variant-id>"},
    "strategies": [{"algorithmID": "murmur"}],
    "characteristics": {"entityType": "featureGroup"}
  },
  "items": [{"id": "<feature-id:string>", "name": "<analytics-feature-key>"}]
}
```

The standalone payload omits `items`; group adds it. Every exposure is a new unlinked event: null parentId/responseId. XDM timestamp comes from the **last evaluation**, whereas envelope timestamp is the later event build time. No identityMap is inserted by this handler; further Edge/Identity enrichment is outside its producer. Queue entries clear before flush callback, and errors are logged/discarded rather than retried. A delivered feature callback does not prove analytics delivery.

Exact schema/producers: `FlagEdgeHandler.java:74-213`; exact fixed strings: `internal/FlagConstants.kt:20-35,65-101`.

### Legacy Offer Decisioning 1.1.0 — real code, separate version-specific surface

Do not substitute modern Optimize/Decisioning APIs. The root README is only a title, but `OfferDecisioning.java`, models, registered listeners, async Edge request construction and cached proposition responses are real production-source code. `OfferDecisioningConstants.java:19-21` reports version **1.1.0**. Source proves implementation, not independently checked remote publication or present-day product support.

#### Public API inventory — 34 entries

All classes are in `com.adobe.marketing.mobile.offer`. Exact aliases:

* **OA** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioning.java`
* **OD** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/DecisionScope.java`
* **OO** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/Offer.java`
* **OP** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/Proposition.java`
* **OX** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferExperienceEvent.java`
* **OT** = `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferType.java`

| ID | Class | Public signature | Profile | Declaration |
|---|---|---|---|---|
| O01 | OfferDecisioning | `static String extensionVersion()` | NO_EVENT | OA:40 |
| O02 | OfferDecisioning | `static void registerExtension()` | OFFER_REGISTER | OA:47 |
| O03 | OfferDecisioning | `static void prefetchOffers(List<DecisionScope> decisionScopes)` | OFFER_PREFETCH | OA:62 |
| O04 | OfferDecisioning | `static void prefetchOffers(List<DecisionScope> decisionScopes, OfferExperienceEvent offerExperienceEvent)` | OFFER_PREFETCH | OA:72 |
| O05 | OfferDecisioning | `static void retrievePrefetchedOffers(List<DecisionScope> decisionScopes, AdobeCallbackWithError<Map<DecisionScope,List<Offer>>> callback)` | OFFER_RETRIEVE | OA:116 |
| O06 | OfferDecisioning | `static void onOfferUpdate(AdobeCallbackWithError<Map<DecisionScope,List<Offer>>> callback)` | OFFER_UPDATES (CALLBACK_ONLY) | OA:163 |
| O07 | DecisionScope | `DecisionScope(String activityId, String placementId)` | NO_EVENT | OD:41 |
| O08 | DecisionScope | `DecisionScope(String activityId, String placementId, int itemCount)` | NO_EVENT | OD:54 |
| O09 | DecisionScope | `String getActivityId()` | NO_EVENT | OD:69 |
| O10 | DecisionScope | `String getPlacementId()` | NO_EVENT | OD:78 |
| O11 | DecisionScope | `int getItemCount()` | NO_EVENT | OD:87 |
| O12 | DecisionScope | `boolean equals(Object o)` | NO_EVENT | OD:164 |
| O13 | DecisionScope | `int hashCode()` | NO_EVENT | OD:176 |
| O14 | Offer | `String getId()` | NO_EVENT | OO:45 |
| O15 | Offer | `String getEtag()` | NO_EVENT | OO:49 |
| O16 | Offer | `OfferType getType()` | NO_EVENT | OO:57 |
| O17 | Offer | `List<String> getLanguage()` | NO_EVENT | OO:61 |
| O18 | Offer | `String getContent()` | NO_EVENT | OO:65 |
| O19 | Offer | `Map<String,String> getCharacteristics()` | NO_EVENT | OO:69 |
| O20 | Offer | `Proposition getProposition()` | NO_EVENT | OO:73 |
| O21 | Offer | `boolean equals(Object o)` | NO_EVENT | OO:78 |
| O22 | Offer | `int hashCode()` | NO_EVENT | OO:97 |
| O23 | Proposition | `String getId()` | NO_EVENT | OP:41 |
| O24 | Proposition | `String getScopeString()` | NO_EVENT | OP:49 |
| O25 | Proposition | `boolean equals(Object o)` | NO_EVENT | OP:84 |
| O26 | Proposition | `int hashCode()` | NO_EVENT | OP:99 |
| O27 | OfferExperienceEvent | `OfferExperienceEvent(Map<String,Object> xdmData)` | NO_EVENT | OX:28 |
| O28 | OfferExperienceEvent | `OfferExperienceEvent(Map<String,Object> xdmData, Map<String,Object> data, String datasetIdentifier)` | NO_EVENT | OX:42 |
| O29 | OfferExperienceEvent | `Map<String,Object> getXdmData()` | NO_EVENT | OX:53 |
| O30 | OfferExperienceEvent | `Map<String,Object> getData()` | NO_EVENT | OX:62 |
| O31 | OfferExperienceEvent | `String getDatasetIdentifier()` | NO_EVENT | OX:71 |
| O32 | OfferExperienceEvent | `boolean equals(Object o)` | NO_EVENT | OX:76 |
| O33 | OfferExperienceEvent | `int hashCode()` | NO_EVENT | OX:88 |
| O34 | OfferType.TEXT | `String toString()` — explicit constant override | NO_EVENT | OT:21 |

Public models have no public track/display/interact/show/dismiss methods in this revision. `Offer`/`Proposition` constructors, `Offer.getSchema`, `Proposition.getOffers` and `getDecisionScope`, and serializers/parsers are package-private, not public callable APIs. No public builder or extension-owned callback interface exists here.

Model behavior: DecisionScope defaults itemCount to 1, clamps values below 1 to 1, converts null IDs to empty strings, and compares/hashes all three fields (`OD:41-89,164-180`). Offer's proposition accessor reads a weak reference; it may return null if the referenced proposition was collected. It does not send a tracking event (`OO:33,73-75`; `OP:27-38`). ExperienceEvent constructors retain supplied maps; the one-argument form uses empty data and empty dataset identifier. Local getters/equals/hashCode have no event effect. `OfferType` values are UNKNOWN/JSON/HTML/IMAGE/TEXT; only TEXT explicitly overrides `toString` to `"text/plain"`, while the others use enum strings (`OT:18-24`). Do not invent MIME conversions or tracking methods.

#### OFFER_REGISTER — O02

`OfferDecisioning.registerExtension → legacy MobileCore.registerExtension → extension constructor → three event listeners`. No Offer request is directly dispatched. Registration failures are logged. This is infrastructure registration, not a catalog request payload.

Listeners are exactly Offer requestContent, Edge `personalization:decisions`, and Edge `com.adobe.eventSource.errorResponseContent` (`OA:47-55`; `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningExtension.java:56-59,78-103`).

#### OFFER_PREFETCH — both O03 and O04

```text
prefetchOffers(scopes) → prefetchOffers(scopes, null)
prefetchOffers(scopes, experienceEvent)
 → validate/truncate scopes → Offer Decisioning Request (type=prefetch)
 → OfferDecisioningExtensionListener → asynchronous Edge request
 → external Edge personalization:decisions response
 → parse/cache propositions → Offer Decisioning Response (notification)
 → any registered onOfferUpdate callback
```

Direct request, with optional experience fields illustrated:

```json
{
  "eventName": "Offer Decisioning Request",
  "eventType": "com.adobe.eventType.offerDecisioning",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "type": "prefetch",
    "decisionscopes": [{
      "activityId": "<activity-id>",
      "placementId": "<placement-id>",
      "itemCount": "<count:int>=1"
    }],
    "xdm": {"<caller-xdm-key>": "<caller-xdm-value>"},
    "data": {"<caller-data-key>": "<caller-data-value>"},
    "datasetId": "<dataset-id>"
  }
}
```

Downstream request:

```json
{
  "eventName": "AEP Request Event",
  "eventType": "com.adobe.eventType.edge",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "xdm": {
      "eventType": "personalization.request",
      "<caller-xdm-key>": "<caller-xdm-value>"
    },
    "query": {
      "personalization": {
        "decisionScopes": ["<Base64.NO_WRAP encoded scope JSON>"]
      }
    },
    "data": {"<caller-data-key>": "<caller-data-value>"},
    "datasetId": "<dataset-id>"
  }
}
```

`xdm`, `data`, `datasetId` in the direct request are included only when the corresponding supplied map/string is nonempty. The downstream always has `xdm.eventType="personalization.request"` and query; optional data/datasetId are copied if supplied. Caller `xdm.eventType` is overwritten. Scope JSON contains `activityId` and `placementId`; `itemCount` is included in encoded JSON only when >1 (`OD:96-138`). Do not confuse direct `decisionscopes` with Edge query `decisionScopes`.

Both of these events are new **unlinked** events; the second is not chained to the first. Null/empty scope list suppresses dispatch. A list longer than 30 is **mutated in place** using `subList(30,size).clear()`; immutable lists can throw before dispatch. Invalid null scope elements may fail serialization; they are not silently filtered (`OA:62-108,192-200`).

The Edge listener constrains type/source and expects `eventData.type="personalization:decisions"` plus a nonempty `payload` list. The eventName of that external response is not constrained by Offer source, so no fictitious fixed Edge response name is provided. Payload parsing resolves scope from Base64 and offers from items, updates the in-memory cache, then emits:

```json
{
  "eventName": "Offer Decisioning Response",
  "eventType": "com.adobe.eventType.offerDecisioning",
  "eventSource": "com.adobe.eventSource.notification",
  "eventData": {
    "propositions": [{
      "id": "<proposition-id>",
      "scope": "<base64-encoded-decision-scope>",
      "items": [{
        "id": "<offer-id>",
        "etag": "<etag>",
        "schema": "<schema-id>",
        "data": {
          "id": "<offer-id>",
          "format": "<OfferType.toString()>",
          "content": "<safe-content>",
          "language": ["<language-code>"],
          "characteristics": {"<characteristic-key>": "<characteristic-value>"}
        }
      }]
    }]
  }
}
```

This notification is unlinked. It is conditional on valid, nonempty parsed results; request dispatch does not guarantee it. Edge error listener logs title/type, and parsing errors are logged, not transformed into a guaranteed public prefetch failure callback.

Exact producers/constants: `OA:62-108,192-200`; `OfferDecisioningExtensionListener.java:33-73`; `OfferDecisioningExtension.java:35-50,111-161,190-255`; `OfferDecisioningConstants.java:24-80,91-124`; `OO:115-145`; `OP:57-80`, all under `code/offer/src/main/java/com/adobe/marketing/mobile/offer/`.

#### OFFER_RETRIEVE — O05

```text
retrievePrefetchedOffers → Offer request(type=retrieve)
 → listener → async in-memory cache lookup
 → Offer response(responseContent), legacy dispatchResponseEvent association
 → parse to Map<DecisionScope,List<Offer>> callback
```

```json
{
  "eventName": "Offer Decisioning Request",
  "eventType": "com.adobe.eventType.offerDecisioning",
  "eventSource": "com.adobe.eventSource.requestContent",
  "eventData": {
    "type": "retrieve",
    "decisionscopes": [{
      "activityId": "<activity-id>",
      "placementId": "<placement-id>",
      "itemCount": "<count:int>=1"
    }]
  }
}
```

```json
{
  "eventName": "Offer Decisioning Response",
  "eventType": "com.adobe.eventType.offerDecisioning",
  "eventSource": "com.adobe.eventSource.responseContent",
  "eventData": {
    "propositions": ["<proposition object using OFFER_PREFETCH notification schema>"]
  }
}
```

This is a local-cache read **with events**, not NO_EVENT. No Edge request. Only cache hits are included; no hits means `propositions:[]` and an empty callback map, not automatic failure. Null/empty scopes invoke `callback.fail(UNEXPECTED_ERROR)` before dispatch; null callback is not guarded. Parse errors and response-callback failures invoke fail; dispatch error callback only logs. Envelope association is the explicit legacy-helper gap described above, not a made-up modern parent chain. Evidence: `OA:116-155`; `OfferDecisioningExtension.java:165-187`.

#### OFFER_UPDATES — O06, CALLBACK_ONLY

`onOfferUpdate` only registers a notification listener; it does not dispatch a request. It observes the exact OFFER_PREFETCH notification payload, constructs a map from decision scope to offer list, and calls the callback. Empty or malformed notification results fail with `UNEXPECTED_ERROR`; listener errors are forwarded. Registration itself is not evidence that a prefetch occurred or succeeded (`OA:163-189`).

### Standalone UserProfile 3.0.1

#### Legacy/duplicate resolution

The specifically requested `aepsdk-core-android/code/userprofile` directory **does not exist** in the reviewed snapshot (native existence check; Scout also rejected exact `code/userprofile` scope). Therefore this inventory is the standalone repository's 3.0.1 surface only. No historical singular-attribute overloads or registration methods are invented or conflated with this checkout.

#### Public API inventory — 4 entries

**UA** = `code/userprofile/src/phone/java/com/adobe/marketing/mobile/UserProfile.java`; class is `com.adobe.marketing.mobile.UserProfile`.

| ID | Public signature | Profile(s) | Declaration |
|---|---|---|---|
| U01 | `static String extensionVersion()` | NO_EVENT | UA:40 |
| U02 | `static void updateUserAttributes(Map<String,Object> attributeMap)` | USER_UPDATE, STATE_USERPROFILE | UA:54 |
| U03 | `static void removeUserAttributes(List<String> attributeNames)` | USER_REMOVE, STATE_USERPROFILE | UA:83 |
| U04 | `static void getUserAttributes(List<String> keys, AdobeCallback<Map<String,Object>> callback)` | USER_GET | UA:113 |

U01 returns `"3.0.1"`, locally. No public model, builder, or extension-owned callback interface exists here. `ProfileData`, `JSONUtils`, exception/constants helpers are package-private; `UserProfileExtension` is infrastructure.

#### USER_UPDATE — U02

```text
updateUserAttributes → requestProfile event → handler → update/delete in-memory attributes
 → persist → only on persistence success: shared state + full-profile unlinked broadcast
```

```json
{
  "eventName": "UserProfileUpdate",
  "eventType": "com.adobe.eventType.userProfile",
  "eventSource": "com.adobe.eventSource.requestProfile",
  "eventData": {
    "userprofileupdatekey": {
      "<attribute-key>": "<safe-value>",
      "<attribute-to-delete>": null
    }
  }
}
```

Null/empty attribute map dispatches nothing. New keys are created, existing values replaced, null values delete attributes. The API returns void, with no business-success callback. Persistence failure does not produce the success broadcast. `ProfileData.persist()` returns true after JSON serialization and `namedCollection.setString` return without exception; this is not an independent disk-durability acknowledgment. Missing ProfileData or invalid event input is logged/ignored (`UA:54-72`; `code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/UserProfileExtension.java:101-129,173-188,463-468`; `code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/ProfileData.java:82-100,151-162`).

#### USER_REMOVE — U03

```text
removeUserAttributes → requestReset event → delete attributes → persist
 → only on persistence success: shared state + full-profile unlinked broadcast
```

```json
{
  "eventName": "RemoveUserProfile",
  "eventType": "com.adobe.eventType.userProfile",
  "eventSource": "com.adobe.eventSource.requestReset",
  "eventData": {"userprofileremovekeys": ["<attribute-key>"]}
}
```

Null/empty list dispatches nothing. A missing key has no data effect; code still attempts persistence for a nonempty list. No per-request public completion callback (`UA:83-102`; `UserProfileExtension.java:132-159,251-267,485-490`).

Both successful mutations use this exact broadcast:

```json
{
  "eventName": "UserProfile Response Event",
  "eventType": "com.adobe.eventType.userProfile",
  "eventSource": "com.adobe.eventSource.responseProfile",
  "eventData": {
    "userprofiledata": {"<remaining-attribute-key>": "<safe-value>"}
  }
}
```

It carries the full remaining profile (possibly `{}`), **not just the changed keys**, and has **null parentId/responseId**. Passing the originating request to `createSharedState` does not correlate the separately built response event (`UserProfileExtension.java:544-561`). The same broadcast can also occur on nonempty persisted-profile load at registration (`:82-98`) or rules consequences; these are not unconditional results of these public methods.

#### USER_GET — U04

```text
getUserAttributes → requestProfile event → requested existing keys read from memory
 → correlated responseProfile → callback(Map<String,Object>)
```

```json
{
  "eventName": "getUserAttributes",
  "eventType": "com.adobe.eventType.userProfile",
  "eventSource": "com.adobe.eventSource.requestProfile",
  "eventData": {"userprofilegetattributes": ["<attribute-key>"]}
}
```

```json
{
  "eventName": "UserProfile Response Event",
  "eventType": "com.adobe.eventType.userProfile",
  "eventSource": "com.adobe.eventSource.responseProfile",
  "eventData": {
    "userprofilegetattributes": {"<existing-attribute-key>": "<safe-value>"}
  }
}
```

Unlike mutation broadcasts, this response uses `inResponseToEvent(request)`: both parentId/responseId are request UUID. No state update, full-profile broadcast, or HTTP call is part of get. Missing/non-null-filtered keys are omitted; an empty result map is valid.

Null callback suppresses dispatch. Null/empty keys call the callback synchronously with an empty map and dispatch **nothing**. Response timeout is 5000ms; errors go only to `AdobeCallbackWithError` when provided; parse failure maps to `UNEXPECTED_ERROR`. Source: `UA:113-192`; `UserProfileExtension.java:200-238`. Constants: `code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/UserProfileConstants.java:37-55`.

### Conditional Core-owned shared-state profiles

STATE_PLACES, STATE_FLAGS, and STATE_USERPROFILE use the **same Core-owned event schema**, with exact stateowner alternatives:

```json
{
  "eventName": "Shared state change",
  "eventType": "com.adobe.eventType.hub",
  "eventSource": "com.adobe.eventSource.sharedState",
  "eventData": {"stateowner": "com.adobe.module.places"}
}
```

| Profile | Exact stateowner | Relevant conditions |
|---|---|---|
| STATE_PLACES | `com.adobe.module.places` | Nearby success, accepted region update, clear, valid authorization update; may also occur at initialization/config privacy reset. |
| STATE_FLAGS | `com.adobe.flags` | Initialization pending-state resolution ready/failed; **not** per-evaluation state. |
| STATE_USERPROFILE | `com.adobe.module.userProfile` | Mutation persistence success; independently on startup with loaded nonempty profile or relevant rules consequence. |

Core emits only if the corresponding state operation is accepted. These notifications do **not** embed the full shared-state map and have null links, even when a state version was associated with an input event.

Exact supporting source: Core `code/core/src/main/java/com/adobe/marketing/mobile/internal/eventhub/EventHub.kt:511-548,600-653,859-867`, `EventHubConstants.kt:20-32`; owner literals in Places `PlacesConstants.java:18`, Flags `internal/FlagConstants.kt:16`, UserProfile `UserProfileConstants.java:17`. Extension call sites are named in the manifest profiles. Producer is Core EventHub; API owner remains the relevant extension.

### Catalog eligibility and exclusions

| Surface/profile | Eligible meaning |
|---|---|
| Public facade dispatch profiles | Direct API-to-event mapping, subject to rejected/no-dispatch paths. |
| Places paired correlated/broadcast responses | Distinct events; do not merge them or count both as two API calls. |
| Places region + Edge, Flags exposure, Offer prefetch Edge/notification | Reviewed **conditional** downstream producers, not unconditional chains. |
| USER_UPDATE/REMOVE full-profile broadcast | Persistence-conditioned, unlinked; event name alone cannot identify the original API call. |
| STATE_* | Core infrastructure notification, not an extension-authored request or full data payload. |
| NO_EVENT | Inventory-only; exclude from “publishes an EventHub event” catalog. |
| OFFER_UPDATES | CALLBACK_ONLY registration/observation, not a direct publisher. |
| OFFER_REGISTER | Infrastructure registration; no invented Offer event payload. |

Explicit exclusions: tests/androidTest/samples, compiler synthetics, public fields/constants, extension-author hooks, protected/private/package-private methods and types, Core/Android/Google owned callback/model definitions, Flags embedded engine/internal implementation. Source effects of excluded helpers are still traced where they implement these public APIs. No unrelated Rules Engine consequence, server response, Edge response completion, or downstream business success is asserted to occur unconditionally.

#### Handoff: changed files, risks, and validation

Patch manifest: **only `places-new.md` and `places-new.json`** are authored documentation deliverables under the artifact root. Scout's attachment metadata is distinct from this documentation patch.

Risks to preserve during consolidation: source-version boundaries; Places duplicate numeric error values; paired broadcasts versus linked replies; Flags grouped aggregation key versus correlationID; conditional exposure after null/false feature results; Offer's caller-list mutation and older Core helper; no invented Offer tracking or legacy UserProfile APIs.

Suggested checks (no SDK builds/tests run): parse JSON; assert unique IDs/profile references; verify all 83 IDs occur in Markdown; retain every profile's source references and optionality. Future runtime validation, if separately authorized, should cover Places enter/exit/cache miss/metadata-null paths, Flags control/aggregation/error callbacks, Offer both prefetch overloads and retrieval cache misses, and UserProfile null-value deletion/empty get inputs/persistence failure.

### Completion evidence

| criterion | status | strongest exact evidence | confidence | gap |
|---|---|---|---|---|
| Places facade/models/local utilities and enums | Complete, 28 entries; UNINDEXED | `code/places/src/phone/java/com/adobe/marketing/mobile/Places.java:55-421`; `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesPOI.java:37-329` | High, direct source | Scout BM25 unavailable after one attach/retry; no index-specific guarantees |
| All Places region/POI downstream behavior | Complete source trace | `code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesState.java:118-193`; `PlacesDispatcher.java:79-97,187-283` | High | Actual Edge transport/backend success not executed or asserted |
| Flags real customer APIs/models | Complete, 17 entries | `code/flags/src/main/java/com/adobe/marketing/mobile/flags/Flag.java:54-246`; `code/flags/build.gradle.kts:33-64` | High | Remote binary publication not independently checked |
| Flags correlated responses/coalesced exposure | Complete source trace | `code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagClientManager.java:222-449`; `FlagEdgeHandler.java:74-213` | High | No runtime delivery guarantee |
| Offer real legacy APIs and both prefetch overloads | Complete, 34 entries | `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioning.java:40-200`; `OfferDecisioningExtension.java:111-255` | High | Remote binary publication not independently checked |
| Legacy Offer retrieve links | Source call verified; exact helper mutation partial | `code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningExtension.java:177-184` | High for call, unknown for helper internals | Targeted current-Core `dispatchResponseEvent` search did not supply the old helper; final parentId/responseId not asserted |
| Standalone UserProfile update/remove/get | Complete, 4 entries | `code/userprofile/src/phone/java/com/adobe/marketing/mobile/UserProfile.java:40-192`; `code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/UserProfileExtension.java:200-238,463-561` | High | None in source-access scope |
| Legacy Core UserProfile duplicate | Absent in requested checkout, not conflated | Native existence check of `aepsdk-core-android/code/userprofile`; exact Scout path scope unresolved | High | No historical module is present to inventory |
| Manifest/Markdown coverage | 83 inventoried / 83 documented | `places-new.json` apiInventory and profile references; matching Markdown inventory IDs | Artifact validation | No builds, installs, commits, pushes, wiki writes, or agents |

---

## Complete payload supplements

Some family sections factor common envelopes into tables or shared examples. The following profile-qualified templates make every inventoried payload body available as parseable JSON in this file. They are illustrative records, not an upload/copy-paste runtime schema; the corresponding profile's gates, optional fields and link rules still apply. A `links` member is explanatory metadata, not eventData.

### `edge-family/edge.responses`

```json
{
  "profile": "edge-family/edge.responses",
  "classification": "CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "AEP Error Response",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.errorResponseContent",
      "eventData": {
        "type": "<error-type>",
        "status": "<integer-status>",
        "title": "<redacted-title>",
        "detail": "<redacted-detail>",
        "eventIndex": "<optional-integer>",
        "requestId": "<network-request-id>",
        "requestEventId": "<attributed-event-id>"
      }
    },
    {
      "eventName": "AEP Response Event Handle",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "type": "<warning-type>",
        "status": "<integer-status>",
        "title": "<redacted-title>",
        "detail": "<redacted-detail>",
        "requestId": "<network-request-id>",
        "requestEventId": "<attributed-event-id>"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/EdgeExtension.java:177-264",
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/EdgeHitProcessor.java:290-365",
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:588-710",
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:754-781",
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:848-898",
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/EdgeConstants.java:25-27",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/EventSource.java:32,44-45"
  ]
}
```

### `edge-family/identity.operational`

```json
{
  "profile": "edge-family/identity.operational",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Edge device attributes request",
      "eventType": "com.adobe.eventType.edgeDeviceAttributes",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "timezone": "<safe-timezone>",
        "tokens": {
          "<token-kind>": "<redacted-token>"
        },
        "<other-allowed-attribute>": "<safe-json-value>",
        "app": {
          "id": "<application-package>",
          "platform": "fcm"
        }
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-edgeidentity-android:code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/Identity.java:249-275",
    "aepsdk-edgeidentity-android:code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/OperationalDataSync.java:85-105,173-234",
    "aepsdk-edgeidentity-android:code/edgeidentity/src/main/java/com/adobe/marketing/mobile/edge/identity/IdentityConstants.java:40-44,181-205"
  ]
}
```

### `edge-family/consent.update`

```json
{
  "profile": "edge-family/consent.update",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Consent Preferences Updated",
      "eventType": "com.adobe.eventType.edgeConsent",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "consents": {
          "collect": {
            "val": "<stored-or-default-value>"
          },
          "metadata": {
            "time": "<ISO8601-trigger-event-time>"
          }
        }
      }
    },
    {
      "eventName": "Edge Consent Update Request",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.updateConsent",
      "eventData": {
        "consents": {
          "collect": {
            "val": "<updated-value>"
          },
          "metadata": {
            "time": "<ISO8601-trigger-event-time>"
          }
        }
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-edgeconsent-android:code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/Consent.java:54-70",
    "aepsdk-edgeconsent-android:code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentExtension.java:163-198,345-386",
    "aepsdk-edgeconsent-android:code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/Consents.java:46-58,92-112,195-200",
    "aepsdk-edgeconsent-android:code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentConstants.java:25,30-34,49-53"
  ]
}
```

### `edge-family/consent.server`

```json
{
  "profile": "edge-family/consent.server",
  "classification": "CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "AEP Response Event Handle",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "consent:preferences",
      "eventData": {
        "type": "consent:preferences",
        "payload": [
          {
            "collect": {
              "val": "<server-consent-value>"
            },
            "metadata": {
              "time": "<optional-server-time>"
            }
          }
        ],
        "requestId": "<network-request-id>",
        "requestEventId": "<optional-attributed-event-id>"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-edge-android:code/edge/src/main/java/com/adobe/marketing/mobile/NetworkResponseHandler.java:588-710",
    "aepsdk-edgeconsent-android:code/edgeconsent/src/main/java/com/adobe/marketing/mobile/edge/consent/ConsentExtension.java:125-133,210-267",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/EventSource.java:39"
  ]
}
```

### `edge-family/bridge.forward`

```json
{
  "profile": "edge-family/bridge.forward",
  "classification": "LISTENER_DRIVEN_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Edge Bridge Request",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "analytics.track",
          "timestamp": "<ISO8601-parent-event-time>"
        },
        "data": {
          "__adobe": {
            "analytics": {
              "linkName": "<action>",
              "linkType": "other",
              "pageName": "<state>",
              "cp": "<foreground|background>",
              "contextData": {
                "<context-key>": "<safe-string>",
                "a.AppID": "<application-name-version>"
              }
            }
          },
          "<additional-input-key>": "<safe-value>"
        }
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-edgebridge-android:code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeExtension.java:58-65,72-86,93-154,167-197,245-405",
    "aepsdk-edgebridge-android:code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeConstants.java:25-65",
    "aepsdk-edgebridge-android:code/edgebridge/src/main/java/com/adobe/marketing/mobile/edge/bridge/EdgeBridgeProperties.java:35-70"
  ]
}
```

### `classic-family/TA.prefetch`

```json
{
  "profile": "classic-family/TA.prefetch",
  "classification": "DIRECT_EVENT",
  "payloads": [
    {
      "eventName": "TargetPrefetchRequest",
      "eventType": "com.adobe.eventType.target",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "prefetch": [
          {
            "name": "<mbox-name>",
            "targetparams": {
              "parameters": {
                "<parameter-key>": "<parameter-value>"
              },
              "profileParameters": {
                "<profile-key>": "<profile-value>"
              },
              "order": {
                "id": "<order-id>",
                "total": 0,
                "purchasedProductIds": [
                  "<product-id>"
                ]
              },
              "product": {
                "id": "<product-id>",
                "categoryId": "<category-id>"
              }
            }
          }
        ],
        "targetparams": {
          "parameters": {
            "<global-key>": "<global-value>"
          },
          "profileParameters": {}
        }
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-target-android/code/target/src/phone/java/com/adobe/marketing/mobile/Target.java:159-241",
    "aepsdk-target-android/code/target/src/main/java/com/adobe/marketing/mobile/target/TargetPrefetch.java:61-69",
    "aepsdk-target-android/code/target/src/main/java/com/adobe/marketing/mobile/target/TargetParameters.java:223-235",
    "aepsdk-target-android/code/target/src/main/java/com/adobe/marketing/mobile/target/TargetConstants.java:135-195"
  ]
}
```

### `classic-family/TA.load`

```json
{
  "profile": "classic-family/TA.load",
  "classification": "DIRECT_EVENT",
  "payloads": [
    {
      "eventName": "TargetLoadRequest",
      "eventType": "com.adobe.eventType.target",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "request": [
          {
            "name": "<mbox-name>",
            "defaultContent": "<fallback-content-or-null>",
            "responsePairId": "<generated-request-pair-uuid>",
            "targetparams": {
              "parameters": {
                "<parameter-key>": "<parameter-value>"
              },
              "profileParameters": {}
            }
          }
        ],
        "targetparams": {
          "parameters": {
            "<global-key>": "<global-value>"
          },
          "profileParameters": {}
        }
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-target-android/code/target/src/phone/java/com/adobe/marketing/mobile/Target.java:268-347",
    "aepsdk-target-android/code/target/src/main/java/com/adobe/marketing/mobile/target/TargetRequest.java:133-152"
  ]
}
```

### `media-optimize/M.TRACK`

```json
{
  "profile": "media-optimize/M.TRACK",
  "classification": "DYNAMIC",
  "payloads": [
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "sessionstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "media.name": "<name>",
          "media.id": "<media-id>",
          "media.length": 60.0,
          "media.streamtype": "vod",
          "media.type": "video",
          "media.resumed": false,
          "media.prerollwaitingtime": 250
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "pause",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "complete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "sessionend",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "error",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "error.id": "<error-id>"
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "qoeupdate",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "qoe.bitrate": 1000.0,
          "qoe.startuptime": 0.0,
          "qoe.fps": 30.0,
          "qoe.droppedframes": 0.0
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "playheadupdate",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "time.playhead": 1.0
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adbreakstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "adbreak.name": "<break-name>",
          "adbreak.position": 1,
          "adbreak.starttime": 0.0
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adbreakcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "ad.name": "<ad-name>",
          "ad.id": "<ad-id>",
          "ad.position": 1,
          "ad.length": 10.0
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adskip",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chapterstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "chapter.name": "<chapter-name>",
          "chapter.position": 1,
          "chapter.length": 10.0,
          "chapter.starttime": 0.0
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chaptercomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chapterskip",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "seekstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "seekcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "bufferstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "buffercomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "bitratechange",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "statestart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "state.name": "<state-name>"
        }
      }
    },
    {
      "eventName": "Media::TrackMedia",
      "eventType": "com.adobe.eventType.media",
      "eventSource": "com.adobe.eventsource.media.trackmedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "stateend",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "state.name": "<state-name>"
        }
      }
    }
  ],
  "sourceRefs": [
    "M/MediaTracker.java:25-81",
    "M/MediaTrackerEventGenerator.java:155-264,270-296,306-355",
    "MI/MediaInternalConstants.java:89-210",
    "MI/MediaCollectionTracker.java:88-175,722-979",
    "C/EventType.java:46"
  ]
}
```

### `media-optimize/E.TRACK`

```json
{
  "profile": "media-optimize/E.TRACK",
  "classification": "DYNAMIC",
  "payloads": [
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "sessionstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "media.name": "<name>",
          "media.id": "<media-id>",
          "media.length": 60,
          "media.streamtype": "vod",
          "media.type": "video",
          "media.resumed": false,
          "media.prerollwaitingtime": 250
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "pause",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "complete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "sessionend",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "error",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "error.id": "<error-id>"
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "qoeupdate",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "qoe.bitrate": 1000,
          "qoe.startuptime": 0,
          "qoe.fps": 30,
          "qoe.droppedframes": 0
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "playheadupdate",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "time.playhead": 1
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adbreakstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "adbreak.name": "<break-name>",
          "adbreak.position": 1,
          "adbreak.starttime": 0
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adbreakcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "ad.name": "<ad-name>",
          "ad.id": "<ad-id>",
          "ad.position": 1,
          "ad.length": 10
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "adskip",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chapterstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "chapter.name": "<chapter-name>",
          "chapter.position": 1,
          "chapter.length": 10,
          "chapter.starttime": 0
        },
        "event.metadata": {
          "<context-key>": "<context-value>"
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chaptercomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "chapterskip",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "seekstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "seekcomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "bufferstart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "buffercomplete",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "bitratechange",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>"
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "statestart",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "state.name": "<state-name>"
        }
      }
    },
    {
      "eventName": "Edge Media TrackMedia",
      "eventType": "com.adobe.eventType.edgeMedia",
      "eventSource": "com.adobe.eventSource.trackMedia",
      "eventData": {
        "trackerid": "<tracker-uuid>",
        "sessionid": "<client-session-uuid>",
        "event.name": "stateend",
        "event.internal": false,
        "event.timestamp": "<Long epoch-ms>",
        "event.param": {
          "state.name": "<state-name>"
        }
      }
    }
  ],
  "sourceRefs": [
    "E/MediaTracker.java:25-81",
    "E/internal/MediaTrackerEventGenerator.java:95-212,219-248,258-306",
    "EI/MediaInternalConstants.java:50-176",
    "EI/MediaEventTracker.java:88-169,719-1003",
    "C/EventType.java:36",
    "C/EventSource.java:47"
  ]
}
```

### `media-optimize/E.XDM`

```json
{
  "profile": "media-optimize/E.XDM",
  "classification": "CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Edge Media - media.sessionComplete",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.sessionComplete",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 60,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/sessionComplete"
        }
      }
    },
    {
      "eventName": "Edge Media - media.sessionEnd",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.sessionEnd",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/sessionEnd"
        }
      }
    },
    {
      "eventName": "Edge Media - media.pauseStart",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.pauseStart",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/pauseStart"
        }
      }
    },
    {
      "eventName": "Edge Media - media.ping",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.ping",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/ping"
        }
      }
    },
    {
      "eventName": "Edge Media - media.error",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.error",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "errorDetails": {
              "name": "<error-id>",
              "source": "player"
            }
          }
        },
        "request": {
          "path": "/va/v1/error"
        }
      }
    },
    {
      "eventName": "Edge Media - media.bufferStart",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.bufferStart",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/bufferStart"
        }
      }
    },
    {
      "eventName": "Edge Media - media.bitrateChange",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.bitrateChange",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "qoeDataDetails": {
              "bitrate": 1000,
              "droppedFrames": 0,
              "framesPerSecond": 30,
              "timeToStart": 0
            }
          }
        },
        "request": {
          "path": "/va/v1/bitrateChange"
        }
      }
    },
    {
      "eventName": "Edge Media - media.adBreakStart",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.adBreakStart",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "advertisingPodDetails": {
              "friendlyName": "<break-name>",
              "index": 1,
              "offset": 0
            }
          }
        },
        "request": {
          "path": "/va/v1/adBreakStart"
        }
      }
    },
    {
      "eventName": "Edge Media - media.adBreakComplete",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.adBreakComplete",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/adBreakComplete"
        }
      }
    },
    {
      "eventName": "Edge Media - media.adStart",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.adStart",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "advertisingDetails": {
              "friendlyName": "<ad-name>",
              "length": 10,
              "name": "<ad-id>",
              "podPosition": 1,
              "playerName": "<configured-player>"
            }
          }
        },
        "request": {
          "path": "/va/v1/adStart"
        }
      }
    },
    {
      "eventName": "Edge Media - media.adSkip",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.adSkip",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/adSkip"
        }
      }
    },
    {
      "eventName": "Edge Media - media.adComplete",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.adComplete",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/adComplete"
        }
      }
    },
    {
      "eventName": "Edge Media - media.chapterSkip",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.chapterSkip",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/chapterSkip"
        }
      }
    },
    {
      "eventName": "Edge Media - media.chapterStart",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.chapterStart",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "chapterDetails": {
              "friendlyName": "<chapter-name>",
              "index": 1,
              "length": 10,
              "offset": 0
            }
          }
        },
        "request": {
          "path": "/va/v1/chapterStart"
        }
      }
    },
    {
      "eventName": "Edge Media - media.chapterComplete",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.chapterComplete",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>"
          }
        },
        "request": {
          "path": "/va/v1/chapterComplete"
        }
      }
    },
    {
      "eventName": "Edge Media - media.statesUpdate",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.statesUpdate",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "statesStart": [
              {
                "name": "<state-name>"
              }
            ]
          }
        },
        "request": {
          "path": "/va/v1/statesUpdate"
        }
      }
    },
    {
      "eventName": "Edge Media - media.statesUpdate",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "media.statesUpdate",
          "timestamp": "<ISO8601 UTC milliseconds>",
          "mediaCollection": {
            "playhead": 1,
            "sessionID": "<backend-session-id>",
            "statesEnd": [
              {
                "name": "<state-name>"
              }
            ]
          }
        },
        "request": {
          "path": "/va/v1/statesUpdate"
        }
      }
    }
  ],
  "sourceRefs": [
    "EI/MediaEventTracker.java:231-349,431-508,719-1003",
    "EI/MediaXDMEventGenerator.kt:46-200,225-305",
    "EI/MediaRealTimeSession.kt:93-107,117-131,137-195",
    "EI/MediaExtension.java:103-144",
    "EI/xdm/XDMMediaEventType.kt:14-39",
    "EI/xdm/XDMMediaEvent.kt:19-27",
    "EI/xdm/XDMMediaSchema.kt:25-34",
    "EI/xdm/XDMMediaCollection.kt:14-77",
    "EI/MediaXDMEventHelper.kt:76-245"
  ]
}
```

### `media-optimize/O.RESPONSE`

```json
{
  "profile": "media-optimize/O.RESPONSE",
  "classification": "CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Optimize Response",
      "eventType": "com.adobe.eventType.optimize",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "responseerror": "<Integer AdobeError code>"
      }
    },
    {
      "eventName": "Optimize Response",
      "eventType": "com.adobe.eventType.optimize",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "responseerror": {
          "type": "<error-type-or-null>",
          "status": 408,
          "title": "<title>",
          "detail": "<detail>",
          "report": null,
          "adobeError": {
            "errorName": "<error-name>",
            "errorCode": "<Integer AdobeError code>"
          }
        },
        "propositions": []
      }
    },
    {
      "eventName": "Optimize Response",
      "eventType": "com.adobe.eventType.optimize",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {}
    }
  ],
  "sourceRefs": [
    "O/OptimizeExtension.java:483-527,839-906,1153-1177",
    "O/OptimizeProposition.java:230-244",
    "O/Offer.java:506-528",
    "O/AEPOptimizeError.kt:35-42",
    "C/Event.java:96-140,195-204",
    "C/util/EventDataUtils.java:149-205"
  ]
}
```

### `media-optimize/O.TAP`

```json
{
  "profile": "media-optimize/O.TAP",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Optimize Track Propositions Request",
      "eventType": "com.adobe.eventType.optimize",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "requesttype": "trackpropositions",
        "propositioninteractions": {
          "eventType": "decisioning.propositionInteract",
          "_experience": {
            "decisioning": {
              "propositions": [
                {
                  "id": "<proposition-id>",
                  "scope": "<scope-name>",
                  "scopeDetails": {},
                  "items": [
                    {
                      "id": "<offer-id>"
                    }
                  ]
                }
              ]
            }
          }
        }
      }
    },
    {
      "eventName": "Edge Optimize Proposition Interaction Request",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "decisioning.propositionInteract",
          "_experience": {
            "decisioning": {
              "propositions": [
                {
                  "id": "<proposition-id>",
                  "scope": "<scope-name>",
                  "scopeDetails": {},
                  "items": [
                    {
                      "id": "<offer-id>"
                    }
                  ]
                }
              ]
            }
          }
        },
        "datasetId": "<configured-dataset-id>"
      }
    }
  ],
  "sourceRefs": [
    "O/Offer.java:293-295,357-378",
    "O/XDMUtils.kt:31-85",
    "O/OptimizeExtension.java:920-981",
    "O/OptimizeConstants.java:174"
  ]
}
```

### `places-new/PLACES_NEARBY`

```json
{
  "profile": "places-new/PLACES_NEARBY",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "role": "response",
      "eventName": "responsegetnearbyplaces",
      "eventType": "com.adobe.eventType.places",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "nearbypois": [
          {
            "regionid": "<poi-id>",
            "regionname": "<poi-name>",
            "latitude": "<latitude:number>",
            "longitude": "<longitude:number>",
            "radius": "<radius:int>",
            "regionmetadata": {
              "<metadata-key>": "<metadata-value>"
            },
            "useriswithin": "<boolean>",
            "libraryid": "<library-id>",
            "weight": "<weight:int>"
          }
        ],
        "status": 0
      },
      "links": {
        "eventId": "<new-response-uuid>",
        "parentId": "<request-eventId>",
        "responseId": "<request-eventId>",
        "timestamp": "<build-epoch-ms>"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-places-android:code/places/src/phone/java/com/adobe/marketing/mobile/Places.java:503-589",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesExtension.java:348-408",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesDispatcher.java:35-76",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesConstants.java:40-50,92-99",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/EventType.java:49",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/EventSource.java:28-32"
  ]
}
```

### `places-new/PLACES_CURRENT`

```json
{
  "profile": "places-new/PLACES_CURRENT",
  "classification": "DIRECT_AND_DOWNSTREAM",
  "payloads": [
    {
      "role": "response",
      "eventName": "responsegetuserwithinplaces",
      "eventType": "com.adobe.eventType.places",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "userwithinpois": [
          {
            "regionid": "<poi-id>",
            "regionname": "<poi-name>",
            "latitude": "<latitude:number>",
            "longitude": "<longitude:number>",
            "radius": "<radius:int>",
            "regionmetadata": {
              "<metadata-key>": "<metadata-value>"
            },
            "useriswithin": true,
            "libraryid": "<library-id>",
            "weight": "<weight:int>"
          }
        ]
      },
      "links": {
        "parentId": "<request-eventId>",
        "responseId": "<request-eventId>"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-places-android:code/places/src/phone/java/com/adobe/marketing/mobile/Places.java:193-266",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesExtension.java:215-228",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesDispatcher.java:100-135",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesState.java:244-253"
  ]
}
```

### `places-new/PLACES_REGION`

```json
{
  "profile": "places-new/PLACES_REGION",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "role": "downstream-places-entry-example",
      "eventName": "responseprocessregionevent",
      "eventType": "com.adobe.eventType.places",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "triggeringregion": {
          "regionid": "<poi-id>",
          "regionname": "<poi-name>",
          "latitude": "<latitude:number>",
          "longitude": "<longitude:number>",
          "radius": "<radius:int>",
          "regionmetadata": {
            "<metadata-key>": "<metadata-value>"
          },
          "useriswithin": true,
          "libraryid": "<library-id>",
          "weight": "<weight:int>"
        },
        "regioneventtype": "entry",
        "timestamp": "<original-request-event.timestamp:epoch-ms>"
      },
      "links": {
        "parentId": null,
        "responseId": null
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-places-android:code/places/src/phone/java/com/adobe/marketing/mobile/Places.java:95-182,432-490",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesState.java:118-193",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesExtension.java:411-441",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesRegion.java:32-65",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesDispatcher.java:79-97,187-283",
    "aepsdk-places-android:code/places/src/main/java/com/adobe/marketing/mobile/places/PlacesConstants.java:165-208"
  ]
}
```

### `places-new/FLAGS_GET`

```json
{
  "profile": "places-new/FLAGS_GET",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "role": "not-served-response",
      "eventName": "Flags Response",
      "eventType": "com.adobe.eventType.flags",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {},
      "links": {
        "parentId": "<request-eventId>",
        "responseId": "<request-eventId>"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/Flag.java:70-148",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagClientManager.java:222-301,403-449",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/internal/FlagConstants.kt:18-56"
  ]
}
```

### `places-new/FLAGS_EXPOSURE`

```json
{
  "profile": "places-new/FLAGS_EXPOSURE",
  "classification": "CONDITIONAL_COALESCED_DOWNSTREAM",
  "payloads": [
    {
      "role": "group-example",
      "eventName": "Flags Edge Feature Exposure",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "request": {
          "path": "/v1/collect"
        },
        "xdm": {
          "eventType": "decisioning.propositionDisplay",
          "timestamp": "<last-evaluated-ISO8601-UTC>",
          "_experience": {
            "decisioning": {
              "propositionEventType": {
                "display": "<positive-count:int>"
              },
              "propositions": [
                {
                  "scopeDetails": {
                    "decisionProvider": "FLAGS",
                    "correlationID": "FG-<group-id>-<variant-id>",
                    "activity": {
                      "id": "FG-<group-id>",
                      "name": "<feature-group-key>"
                    },
                    "experience": {
                      "id": "Variant-<variant-id>"
                    },
                    "strategies": [
                      {
                        "algorithmID": "murmur"
                      }
                    ],
                    "characteristics": {
                      "entityType": "featureGroup"
                    }
                  },
                  "items": [
                    {
                      "id": "<feature-id:string>",
                      "name": "<analytics-feature-key>"
                    }
                  ]
                }
              ]
            }
          }
        }
      },
      "links": {
        "parentId": null,
        "responseId": null
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagClientManager.java:62-70,198-218,382-400",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/ExposureEventIdGenerator.java:36-110",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FeatureExposureQueue.java:89-94,149-205,228-258,286-395",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagEdgeHandler.java:74-213",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/internal/FlagConstants.kt:65-101,124-127"
  ]
}
```

### `places-new/OFFER_PREFETCH`

```json
{
  "profile": "places-new/OFFER_PREFETCH",
  "classification": "DIRECT_AND_CONDITIONAL_DOWNSTREAM",
  "payloads": [
    {
      "role": "downstream-edge",
      "eventName": "AEP Request Event",
      "eventType": "com.adobe.eventType.edge",
      "eventSource": "com.adobe.eventSource.requestContent",
      "eventData": {
        "xdm": {
          "eventType": "personalization.request",
          "<caller-xdm-key>": "<caller-xdm-value>"
        },
        "query": {
          "personalization": {
            "decisionScopes": [
              "<Base64.NO_WRAP JSON activityId/placementId, itemCount included only if >1>"
            ]
          }
        },
        "data": {
          "<caller-data-key>": "<caller-data-value>"
        },
        "datasetId": "<dataset-id>"
      },
      "links": {
        "parentId": null,
        "responseId": null
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioning.java:62-108,192-200",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningConstants.java:24-80",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningExtensionListener.java:33-73",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningExtension.java:35-50,111-161,208-255",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/Offer.java:133-145",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/Proposition.java:71-80"
  ]
}
```

### `places-new/OFFER_RETRIEVE`

```json
{
  "profile": "places-new/OFFER_RETRIEVE",
  "classification": "DIRECT_AND_DOWNSTREAM",
  "payloads": [
    {
      "role": "response",
      "eventName": "Offer Decisioning Response",
      "eventType": "com.adobe.eventType.offerDecisioning",
      "eventSource": "com.adobe.eventSource.responseContent",
      "eventData": {
        "propositions": [
          {
            "id": "<proposition-id>",
            "scope": "<base64-encoded-decision-scope>",
            "items": [
              {
                "id": "<offer-id>",
                "etag": "<etag>",
                "schema": "<schema-id>",
                "data": {
                  "id": "<offer-id>",
                  "format": "<OfferType.toString()>",
                  "content": "<safe-content>",
                  "language": [
                    "<language-code>"
                  ],
                  "characteristics": {
                    "<characteristic-key>": "<characteristic-value>"
                  }
                }
              }
            ]
          }
        ]
      },
      "links": {
        "association": "MobileCore.dispatchResponseEvent(responseEvent, requestEvent, errorCallback)",
        "builderParentId": null,
        "builderResponseId": null,
        "finalParentId": "UNVERIFIED legacy Core helper",
        "finalResponseId": "UNVERIFIED legacy Core helper; response association requested"
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioning.java:116-155",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningExtension.java:165-187",
    "aepsdk-offer-android:code/offer/src/main/java/com/adobe/marketing/mobile/offer/OfferDecisioningConstants.java:24-80"
  ]
}
```

### `places-new/STATE_FLAGS`

```json
{
  "profile": "places-new/STATE_FLAGS",
  "classification": "CONDITIONAL_CORE_INFRASTRUCTURE_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Shared state change",
      "eventType": "com.adobe.eventType.hub",
      "eventSource": "com.adobe.eventSource.sharedState",
      "eventData": {
        "stateowner": "com.adobe.flags"
      },
      "links": {
        "parentId": null,
        "responseId": null
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagExtension.java:167-186",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/FlagClientManager.java:91-145",
    "aepsdk-flags-android:code/flags/src/main/java/com/adobe/marketing/mobile/flags/internal/FlagConstants.kt:16,59-63",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/internal/eventhub/EventHub.kt:600-653,859-867"
  ]
}
```

### `places-new/STATE_USERPROFILE`

```json
{
  "profile": "places-new/STATE_USERPROFILE",
  "classification": "CONDITIONAL_CORE_INFRASTRUCTURE_DOWNSTREAM",
  "payloads": [
    {
      "eventName": "Shared state change",
      "eventType": "com.adobe.eventType.hub",
      "eventSource": "com.adobe.eventSource.sharedState",
      "eventData": {
        "stateowner": "com.adobe.module.userProfile"
      },
      "links": {
        "parentId": null,
        "responseId": null
      }
    }
  ],
  "sourceRefs": [
    "aepsdk-userprofile-android:code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/UserProfileExtension.java:544-561",
    "aepsdk-userprofile-android:code/userprofile/src/main/java/com/adobe/marketing/mobile/userprofile/UserProfileConstants.java:17",
    "aepsdk-core-android:code/core/src/main/java/com/adobe/marketing/mobile/internal/eventhub/EventHub.kt:511-548,859-867"
  ]
}
```

## Existing selected catalog example (not the full inventory)

The earlier proposed 16-name catalog is retained below as a **limited schema-compatible example**, not as a claim that every API above is observable or safely name-attributable. The runtime catalog is unchanged by this document. Do not equate this example with activation or automatic catalog generation from every downstream event.

This example uses schemaVersion 1/catalogVersion 2. The current strict POC parser allows at most 64 mappings and 65,536 characters; the full diagnostic API/payload reference is deliberately not that asset format. Review internal origins and ambiguities before packaging any mapping.

```json
{
  "schemaVersion": 1,
  "catalogVersion": 2,
  "sourceCategories": {
    "com.adobe.eventSource.requestContent": "API_CALL",
    "com.adobe.eventSource.requestIdentity": "API_CALL",
    "com.adobe.eventSource.requestReset": "API_CALL",
    "com.adobe.eventSource.responseContent": "API_RESPONSE",
    "com.adobe.eventSource.responseIdentity": "API_RESPONSE",
    "com.adobe.eventSource.contentComplete": "API_RESPONSE",
    "com.adobe.eventSource.errorResponseContent": "API_RESPONSE"
  },
  "mappings": [
    {
      "eventName": "Configure with App ID",
      "apiName": "configureWithAppID",
      "extension": "com.adobe.module.core"
    },
    {
      "eventName": "Configuration Update",
      "apiNames": [
        "updateConfiguration",
        "setPrivacyStatus"
      ],
      "extension": "com.adobe.module.core"
    },
    {
      "eventName": "Analytics Track",
      "apiNames": [
        "trackAction",
        "trackState"
      ],
      "extension": "com.adobe.module.core"
    },
    {
      "eventName": "Lifecycle Resume",
      "apiName": "lifecycleStart",
      "extension": "com.adobe.module.core"
    },
    {
      "eventName": "Lifecycle Pause",
      "apiName": "lifecyclePause",
      "extension": "com.adobe.module.core"
    },
    {
      "eventName": "Refresh in-app messages",
      "apiName": "refreshInAppMessages",
      "extension": "com.adobe.messaging"
    },
    {
      "eventName": "Update propositions",
      "apiName": "updatePropositionsForSurfaces",
      "extension": "com.adobe.messaging"
    },
    {
      "eventName": "Get propositions",
      "apiName": "getPropositionsForSurfaces",
      "extension": "com.adobe.messaging"
    },
    {
      "eventName": "Message propositions response",
      "apiName": "getPropositionsForSurfaces",
      "extension": "com.adobe.messaging"
    },
    {
      "eventName": "Finalize propositions response",
      "apiNames": [
        "refreshInAppMessages",
        "updatePropositionsForSurfaces"
      ],
      "extension": "com.adobe.messaging"
    },
    {
      "eventName": "AEP Request Event",
      "apiName": "sendEvent",
      "extension": "com.adobe.edge"
    },
    {
      "eventName": "IdentityRequestIdentity",
      "apiNames": [
        "getIdentifiers",
        "getExperienceCloudId",
        "getUrlVariables",
        "appendVisitorInfoForURL",
        "syncIdentifiers",
        "syncIdentifier"
      ],
      "extension": "com.adobe.module.identity"
    },
    {
      "eventName": "CampaignClassic Register Device",
      "apiName": "registerDevice",
      "extension": "com.adobe.module.campaignclassic"
    },
    {
      "eventName": "Device Registration Status",
      "apiName": "registerDevice",
      "extension": "com.adobe.module.campaignclassic"
    },
    {
      "eventName": "CampaignClassic Track Notification Receive",
      "apiName": "trackNotificationReceive",
      "extension": "com.adobe.module.campaignclassic"
    },
    {
      "eventName": "CampaignClassic Track Notification Click",
      "apiName": "trackNotificationClick",
      "extension": "com.adobe.module.campaignclassic"
    }
  ]
}
```

The JSON above is the historical schema-1 proposal, not the current runtime asset. The subsequent local schema-2 implementation and generated projection are described in the runtime implementation update near the beginning of this document. No wiki page, commit or push is part of that implementation.
