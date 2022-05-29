package core.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitLib

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl