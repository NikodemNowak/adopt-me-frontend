package com.nikodem.adoptme.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikodem.adoptme.db.AppDatabase
import com.nikodem.adoptme.repositories.AdoptMeRepository
import com.nikodem.adoptme.repositories.AdoptMeRepositoryImpl
import com.nikodem.adoptme.repositories.CachedAdoptMeRepository
import com.nikodem.adoptme.repositories.CachedAdoptMeRepositoryImpl
import com.nikodem.adoptme.ui.confirm_otp_code.ConfirmOtpCodeFragmentViewModel
import com.nikodem.adoptme.ui.end_logging_in.EndLoggingInFragmentViewModel
import com.nikodem.adoptme.ui.end_registration.EndRegistrationFragmentViewModel
import com.nikodem.adoptme.ui.favorites.FavoritesFragmentViewModel
import com.nikodem.adoptme.ui.form.FormFragmentViewModel
import com.nikodem.adoptme.ui.home_screen.HomeScreenFragmentViewModel
import com.nikodem.adoptme.ui.login.LoginFragmentViewModel
import com.nikodem.adoptme.ui.login_or_register.LoginOrRegisterFragmentViewModel
import com.nikodem.adoptme.ui.main.MainFragmentViewModel
import com.nikodem.adoptme.ui.profile.ProfileFragmentViewModel
import com.nikodem.adoptme.ui.register_shelter.RegisterShelterFragmentViewModel
import com.nikodem.adoptme.ui.register_user.RegisterUserFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FavoritesFragmentViewModel() }

    viewModel { MainFragmentViewModel() }

    viewModel { HomeScreenFragmentViewModel() }

    viewModel { ProfileFragmentViewModel() }

    viewModel { FormFragmentViewModel(questionAnswersRepository = get(), get()) }

    viewModel { LoginOrRegisterFragmentViewModel() }

    viewModel { RegisterUserFragmentViewModel() }

    viewModel { ConfirmOtpCodeFragmentViewModel() }

    viewModel { EndRegistrationFragmentViewModel() }

    viewModel { RegisterShelterFragmentViewModel() }

    viewModel {
        LoginFragmentViewModel()
    }

    viewModel {
        EndLoggingInFragmentViewModel()
    }

    single<AdoptMeRepository> {
        AdoptMeRepositoryImpl(adoptMeApiService = get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "database-name"
        ).build()
    }

    single<CachedAdoptMeRepository> {
        CachedAdoptMeRepositoryImpl(
            userDao = get<AppDatabase>().userDao()
        )
    }
}