# Technical Interview Scenarios - Listing Service

Welcome to the technical interview for the Listing Service project. This repository contains a Spring Boot application that manages real estate listings (Sites, Plots of Land, etc.).

Below are the scenarios designed for different seniority levels. Candidates are expected to implement the requirements while following the existing architecture and best practices.

## General Instructions
- All code changes should be accompanied by relevant unit or integration tests.
- Maintain consistency with the existing code style and architectural patterns (Hexagonal/Ports & Adapters).
- Ensure that the application still builds and all tests pass.

---

## Junior Scenarios (1-2)

### Scenario 1: Input Validation
**Objective:** Add validation to the Site creation process.
**Requirement:**
- The `name` of a Site must not be null or empty.
- The `totalSiteArea` must be a positive value.
- If validation fails, the API should return a `400 Bad Request` with a meaningful error message.
**Expected Outcome:**
- Updated `CreateSiteRequest` or a dedicated validation layer.
- Tests demonstrating that invalid requests are rejected.

### Scenario 2: New Field - "Currency"
**Objective:** Extend the Site entity with a new property.
**Requirement:**
- Add a `currency` field (e.g., USD, NGN, EUR) to the `Site` entity and its corresponding DTOs.
- This field should be mandatory for both creation and updates.
- Update the Liquibase migration scripts to include the new column in the `SITE` table.
**Expected Outcome:**
- Database schema updated via Liquibase.
- Entity and DTOs updated.
- Integration tests verifying the persistence and retrieval of the currency field.

---

## Intermediate Scenarios (3-4)

### Scenario 3: Business Logic - Automatic State Transition
**Objective:** Implement a business rule that affects the state of a Site.
**Requirement:**
- When a Site is created, it starts in the `DRAFT` state (already implemented).
- Implement an endpoint (or extend `patchSiteById`) to "publish" a Site.
- A Site can only be transitioned to `PUBLISHED` if it has an `address` and at least one `coverImage` assigned.
- If these conditions are not met, return a custom exception with a `422 Unprocessable Entity` status.
**Expected Outcome:**
- Logic implemented in `ListingManager`.
- Custom exception handling.
- Tests covering both successful and failed transitions.

### Scenario 4: Querying and Filtering
**Objective:** Enhance the list sites endpoint with filtering capabilities.
**Requirement:**
- Update the `listSites` endpoint to support filtering by `state` (e.g., `GET /listings/sites?state=PUBLISHED`).
- The filtering should happen at the database level (Spring Data JPA).
- If no state is provided, return all non-deleted sites.
**Expected Outcome:**
- Updated `SiteRepository` and `JpaSiteRepository`.
- Modified API contract and controller implementation.
- Tests verifying that filtering works as expected.

---

## Senior Scenario (5 + Bonus)

### Scenario 5: Soft Delete Consistency and Audit
**Objective:** Refine the soft-delete mechanism and add basic auditing.
**Requirement:**
- Currently, when a Site is soft-deleted, its associated `PlotOfLand` records remain unchanged. Implement a cascading soft-delete: when a Site is marked as `deleted`, all associated `PlotOfLand` records must also be marked as `deleted`.
- Introduce `createdBy` and `lastModifiedBy` fields to the `BaseEntity`. For the purpose of this interview, you can use a hardcoded string or a simple mock to represent the current user.
- Ensure these fields are automatically populated using Spring Data JPA Auditing.
**Expected Outcome:**
- Cascading logic implemented in the service layer or via JPA hooks.
- `BaseEntity` and database schema updated for auditing.
- Tests demonstrating the cascade effect and the population of audit fields.

### Bonus (Senior): Performance and Concurrency
**Objective:** Optimize the "List Sites" endpoint and handle concurrent updates.
**Requirement:**
- Imagine the `SITE` table grows to millions of rows. Implement pagination for the `listSites` endpoint (e.g., `page` and `size` parameters).
- Implement Optimistic Locking on the `Site` entity to prevent lost updates when two administrators are editing the same site simultaneously.
- Explain (or briefly sketch) how you would handle searching for sites by description using a full-text search approach if the requirement arose.
**Expected Outcome:**
- Paginated response in the API.
- `@Version` field added to the entity.
- Tests or a demonstration of how a `StaleObjectStateException` would be handled.
