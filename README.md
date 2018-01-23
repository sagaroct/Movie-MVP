# Movie-MVP
Movie sample app with MVP architecture.

This is a small sample app to demonstrate how you could integrate the following in your own android app with MVP architecture:
 1) Dagger2 for dependency injection,
 2) Realm for database,
 3) Retrofit for API,
 4) RxJava for API and event handling,
 5) Local unit testing using JUnit and Mockito,
 6) Instrumented(Functional) Unit test using Espresso.


Dagger 2 annotations :

@Inject  — base annotation whereby the “dependency is requested”
@Module — classes in which methods “provide dependencies”
@Provide — methods inside @Module, which “tell Dagger how we want to build and present a dependency“
@Component — bridge between @Inject and @Module
@Scope — enables to create global and local singletons
@Qualifier(@Named) — if different objects of the same type are necessary.

NOTE for Instrumented Unit test: Roboelectric can be used for instrumented tests as they can run on JVM(which is faster),
 using Espresso instrumented tests should be run on Android system.
 Local Unit tests runs on JVM.