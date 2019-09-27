

import com.imagine.readtheposts.view.di.NetworkModule
import com.imagine.readtheposts.view.dialog.DialogFactory
import com.imagine.therickandmorty.data.mapper.CharactersMapper
import com.imagine.therickandmorty.data.mapper.EpisodeMapper
import com.imagine.therickandmorty.data.network.ApiService
import com.imagine.therickandmorty.data.repository.Repository
import com.imagine.therickandmorty.data.repository.RickAndMortyRepository
import com.imagine.therickandmorty.data.utils.RequestUtils
import com.imagine.therickandmorty.view.utils.StringFormatter


object CharactersModule {

    private var repository: Repository? = null
    private var dialogFactory: DialogFactory? = null
    private var stringFormatter: StringFormatter? = null

    fun getRepository(): Repository {
        if (repository == null)
            repository = RickAndMortyRepository(
                NetworkModule.getService(ApiService::class.java),
                RequestUtils(),
                CharactersMapper(),
                EpisodeMapper()
            )
        return repository!!
    }

    fun getDialogFactory(): DialogFactory {
        if (dialogFactory == null) dialogFactory = DialogFactory()
        return dialogFactory!!
    }

    fun getStringFormatter() : StringFormatter {
        if (stringFormatter == null) {
            stringFormatter = StringFormatter()
        }
        return stringFormatter!!
    }
}