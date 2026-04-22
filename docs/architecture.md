#  Architecture

This project adopts a multi-module architecture to separate concerns, improve code reusability, and isolate UI paradigms.

## Modules Overview

### 1. `:core` (Shared Module)
A Kotlin/Android Library module that encapsulates all data access and business logic.
- **Data Models**: Data classes representing the domain entities (e.g., `DogImage`, `Favorite`).
- **Remote API**: Configurations for HTTP communication (Retrofit/Ktor).
- **Repository**: Single source of truth for accessing remote data and handling local caching.

### 2. `:app-xml` (Legacy UI)
An Android application module using traditional XML view layouts.
- Relies exclusively on Activities/Fragments with View Binding.
- Imports `implementation(project(":core"))` to consume shared data services.
- Acts as the baseline, legacy implementation.

### 3. `:app-compose` (Modern Declarative UI)
An Android application module using Jetpack Compose exclusively.
- Implements declarative UI patterns.
- Imports `implementation(project(":core"))` mapping the shared data directly to State and Composables.
- Demonstrates modern Android UI practices and features exclusive to Compose environments.
