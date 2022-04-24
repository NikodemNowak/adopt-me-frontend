package com.nikodem.adoptme.di

import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.nikodem.adoptme.db.AppDatabase
import com.nikodem.adoptme.repositories.*
import com.nikodem.adoptme.ui.confirm_otp_code.*
import com.nikodem.adoptme.ui.end_logging_in.EndLoggingInFragmentViewModel
import com.nikodem.adoptme.ui.end_registration.EndRegistrationFragmentViewModel
import com.nikodem.adoptme.ui.favorites.FavoritesFragmentViewModel
import com.nikodem.adoptme.ui.form.FormFragmentViewModel
import com.nikodem.adoptme.ui.home_screen.AnimalMediator
import com.nikodem.adoptme.ui.home_screen.HomeScreenFragmentViewModel
import com.nikodem.adoptme.ui.home_screen.details_screen.DetailsScreenFragmentViewModel
import com.nikodem.adoptme.ui.login.LoginFragmentViewModel
import com.nikodem.adoptme.ui.login_or_register.LoginOrRegisterFragmentViewModel
import com.nikodem.adoptme.ui.main.MainFragmentViewModel
import com.nikodem.adoptme.ui.profile.ProfileFragmentViewModel
import com.nikodem.adoptme.ui.profile_screen.ProfileScreenFragmentViewModel
import com.nikodem.adoptme.ui.register_shelter.RegisterShelterFragmentViewModel
import com.nikodem.adoptme.ui.register_user.RegisterUserFragmentViewModel
import com.nikodem.adoptme.ui.splash_screen.SplashScreenFragmentViewModel
import com.nikodem.adoptme.usecases.*
import okhttp3.internal.cacheGet
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModel { FavoritesFragmentViewModel() }

    viewModel { MainFragmentViewModel() }

    viewModel {
        HomeScreenFragmentViewModel(
            getAnimalsUseCase = get(),
            animalMediator = AnimalMediator(
                database = get(),
                adoptMeRepository = get()
            ),
            animalDao = get<AppDatabase>().animalDao()
        )
    }

    viewModel { DetailsScreenFragmentViewModel() }

    viewModel { ProfileScreenFragmentViewModel(getUserUseCase = get()) }

    viewModel { ProfileFragmentViewModel() }

    viewModel {
        FormFragmentViewModel(
            getQuestionAnswersUseCase = get(),
            addUserPreferenceUseCase = get()
        )
    }

    viewModel { LoginOrRegisterFragmentViewModel() }

    viewModel {
        RegisterUserFragmentViewModel(
            addUserUseCase = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        ConfirmOtpCodeFragmentViewModel(
            savedStateHandle = get(),
            verifyOtpUseCase = get()
        )
    }

    viewModel { EndRegistrationFragmentViewModel(setPinUseCase = get()) }

    viewModel { RegisterShelterFragmentViewModel() }

    viewModel {
        LoginFragmentViewModel()
    }

    viewModel {
        EndLoggingInFragmentViewModel()
    }

    viewModel {
        SplashScreenFragmentViewModel(
            isLoggedInUseCase = get()
        )
    }

    single<AdoptMeRepository> {
        AdoptMeRepositoryImpl(
            adoptMeApiService = get(),
            networkHandler = get(),
            tokenRepository = get()
        )
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "database-name"
        ).build()
    }

    single<CachedAdoptMeRepository> {
        get<CacheManager>().registerCache(
            CachedAdoptMeRepositoryImpl(
                userDao = get<AppDatabase>().userDao(),
                questionAnswersDao = get<AppDatabase>().questionAnswersDao(),
                animalDao = get<AppDatabase>().animalDao()
            )
        )
    }

    factory<GetQuestionAnswersUseCase> {
        GetQuestionAnswersUseCaseImpl(
            cachedAdoptMeRepository = get(),
            adoptMeRepository = get()
        )
    }

    factory<GetAnimalsUseCase> {
        GetAnimalsUseCaseImpl(
            cachedAdoptMeRepository = get(),
            adoptMeRepository = get()
        )
    }

    factory<AddUserPreferenceUseCase> {
        AddUserPreferenceUseCaseImpl(
            adoptMeRepository = get()
        )
    }

    factory<AddUserUseCase> {
        AddUserUseCaseImpl(
            adoptMeRepository = get(),
            tokenRepository = get()
        )
    }

    factory<VerifyOtpUseCase> {
        VerifyOtpUseCaseImpl(
            adoptMeRepository = get(),
            tokenRepository = get()
        )
    }

    factory<SetPinUseCase> {
        SetPinUseCaseImpl(
            adoptMeRepository = get(),
            tokenRepository = get()
        )
    }

    factory<ResetRegistrationUseCase> {
        ResetRegistrationUseCaseImpl(
            adoptMeRepository = get(),
            cacheManager = get()
        )
    }

    factory<GetUserUseCase> {
        GetUserCaseImpl(
            tokenRepository = get(),
            adoptMeRepository = get()
        )
    }

    factory<IsLoggedInUseCase> {
        IsLoggedInUseCaseImpl(
            tokenRepository = get()
        )
    }

    single<CacheManager> {
        CacheManagerImpl()
    }

    single<TokenRepository> {
        get<CacheManager>().registerCache(TokenRepositoryImpl(sharedPreferences = get()))
    }

    single {
        EncryptedSharedPreferences.create(
            "sharedPrefsFile",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            androidContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } bind SharedPreferences::class

    single<SmsClient> {
        SmsClientImpl(androidContext())
    }

    single<SmsHandler> {
        SmsHandlerImpl(
            client = get()
        )
    }
}