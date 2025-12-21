# Technical Interview Scenarios - Listing Service

Welcome to the technical interview for the Listing Service project. This repository contains a Spring Boot application that manages real estate listings (Sites, Plots of Land, etc.).

Below are the scenarios designed for different seniority levels. Candidates are expected to implement the requirements while following the existing architecture and best practices.

## General Instructions
- All code changes should be accompanied by relevant unit or integration tests.
- Maintain consistency with the existing code style and architectural patterns (Hexagonal/Ports & Adapters).
- Ensure that the application still builds and all tests pass.

---

## Domain Model Overview

The service manages two main entities:
- **Site**: Represents a real estate project or development area.
- **Plot of Land (Parcel)**: A specific piece of land within a Site.

### Plot of Land Shape
A `PlotOfLand` should have the following properties:
- `parcelId`: A unique identifier within the site (e.g., "Block-A-01").
- `area`: The area of the plot (BigDecimal).
- `status`: The current availability (Enum: `AVAILABLE`, `RESERVED`, `SOLD`).
- `price`: Optional price for the specific plot. If not set, the Site's default price per parcel is used.
- `site`: The parent `Site` it belongs to.

---

## Junior Scenarios (1-2)

### Scenario 1: Input Validation
**Objective:** Add validation to the Site creation process.
**Requirement:**
- The `name` of a Site must not be null or empty.
- The `totalSiteArea` must be a positive value.
- If validation fails, the API should return a `400 Bad Request` with a meaningful error message.
**Expected Outcome:**
- Validation logic implemented and integrated into the API.
- Error responses following the project's standard format.
- Tests demonstrating that invalid requests are handled correctly.

### Scenario 2: New Field - "Currency"
**Objective:** Extend the Site entity with a new property.
**Requirement:**
- Add a `currency` field (e.g., USD, NGN, EUR) to the `Site` entity and its corresponding DTOs.
- This field should be mandatory for both creation and updates.
- Update the Liquibase migration scripts to include the new column in the `SITE` table.
**Expected Outcome:**
- Database schema updated to include the new field.
- Persistence and API layers updated to support the new field.
- Integration tests verifying the end-to-end functionality of the currency field.

---

## Intermediate Scenarios (3-4)

### Scenario 3: Business Logic - Automatic State Transition
**Objective:** Implement a business rule that affects the state of a Site.
**Requirement:**
- When a Site is created, it starts in the `DRAFT` state.
- Implement a "Publish" operation.
- A Site can only be transitioned to `PUBLISHED` if it has an `address` and at least one `coverImage` assigned.
- If these conditions are not met, return a custom exception with a `422 Unprocessable Entity` status.
**Expected Outcome:**
- Business rules implemented in the domain/service layer.
- Custom error handling for invalid state transitions.
- Tests covering both successful and failed transitions.

### Scenario 4: Plot of Land Management
**Objective:** Implement the Plot of Land entity and its management API.
**Requirement:**
- Implement the `PlotOfLand` entity with the following properties:
    - `parcelId`: Unique identifier (e.g., "Block-A-01").
    - `area`: Plot area (BigDecimal).
    - `status`: Availability (AVAILABLE, RESERVED, SOLD).
    - `price`: Optional price.
    - `site`: Parent Site.
- Implement its relationship (Many-to-One) with `Site`.
- Create endpoints to manage plots within a site:
    - `POST /listings/sites/{siteId}/plots`: Add a new plot to a site.
        - *Rule:* `parcelId` must be unique within the same Site.
        - *Rule:* Total area of plots cannot exceed the Site's `totalSiteArea`.
    - `PATCH /listings/plots/{plotId}`: Update plot details (area, status, price).
        - *Rule:* A plot marked as `SOLD` cannot have its `area` or `price` changed.
    - `DELETE /listings/plots/{plotId}`: Soft-delete a plot.
        - *Rule:* Marking a plot as deleted should also change its status to `ARCHIVED` (or similar).
- Ensure that when a Site is retrieved, it optionally includes its list of plots.
**Expected Outcome:**
- Data model and persistence layer updated to support plots.
- API endpoints for managing plots and their lifecycle.
- Updated retrieval of Sites to optionally include their associated plots.
- Database schema updated to reflect the new entity and relationship.

---

## Senior Scenario (5 + Bonus)

### Scenario 5: Soft Delete Consistency and Audit
**Objective:** Refine the soft-delete mechanism and add basic auditing.
**Requirement:**
- Implement cascading soft-delete: when a Site is marked as `deleted`, all associated `PlotOfLand` records must also be marked as `deleted`.
- Introduce `createdBy` and `lastModifiedBy` fields to the `BaseEntity`.
- Ensure these fields are automatically populated using Spring Data JPA Auditing.
**Expected Outcome:**
- Cascading logic implemented according to the requirement.
- Base infrastructure and database schema updated for auditing.
- Tests demonstrating the cascade effect and the automatic population of audit fields.

### Bonus (Senior): Performance and Concurrency
**Objective:** Optimize the "List Sites" endpoint and handle concurrent updates.
**Requirement:**
- Imagine the `SITE` table grows to millions of rows. Implement pagination for the `listSites` endpoint (e.g., `page` and `size` parameters).
- Implement Optimistic Locking on the `Site` entity to prevent lost updates when two administrators are editing the same site simultaneously.
- Explain (or briefly sketch) how you would handle searching for sites by description using a full-text search approach if the requirement arose.
**Expected Outcome:**
- Paginated response in the API.
- Mechanism for handling concurrent updates implemented.
- Tests or a demonstration of how concurrent update conflicts are handled.
