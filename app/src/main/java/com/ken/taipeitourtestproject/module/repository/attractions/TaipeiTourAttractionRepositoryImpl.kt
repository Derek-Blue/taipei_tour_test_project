package com.ken.taipeitourtestproject.module.repository.attractions

import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsCategory
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsData
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsFile
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsImage
import com.ken.taipeitourtestproject.module.repository.attractions.data.RepositoryAttractionsLink
import com.ken.taipeitourtestproject.module.repository.attractions.request.RepositoryLanguageType
import com.ken.taipeitourtestproject.module.service.TaipeiTourWeb
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaipeiTourAttractionRepositoryImpl(
    private val taipeiTourWeb: TaipeiTourWeb,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
): TaipeiTourAttractionRepository {

    override suspend fun getData(
        languageType: RepositoryLanguageType,
        page: Int
    ): Result<List<RepositoryAttractionsData>> {
        return withContext(defaultDispatcher) {
            taipeiTourWeb.getAttractions(languageType.tag, page).mapCatching { response ->
                response.data?.mapNotNull { data ->
                    RepositoryAttractionsData(
                        id = data.id ?: 0L,
                        name = data.name ?: return@mapNotNull null,
                        nameZh = data.nameZh ?: "",
                        openStatus = data.openStatus ?: 0,
                        introduction = data.introduction ?: "",
                        openTime = data.openTime ?: "",
                        zipcode = data.zipcode ?: "",
                        distric = data.distric ?: "",
                        address = data.address ?: "",
                        tel = data.tel ?: "",
                        fax = data.fax ?: "",
                        email = data.email ?: "",
                        months = data.months ?: "",
                        nlat = data.nlat ?: Double.NaN,
                        elong = data.elong ?: Double.NaN,
                        officialSite = data.officialSite ?: "",
                        facebook = data.facebook ?: "",
                        ticket = data.ticket ?: "",
                        remind = data.remind ?: "",
                        stayTime = data.stayTime ?: "",
                        modified = data.modified ?: "",
                        url = data.url ?: "",
                        category = data.category?.mapNotNull categoryMap@ {
                            RepositoryAttractionsCategory(
                                id = it.id ?: 0L,
                                name = it.name ?:  return@categoryMap null
                            )
                        } ?: emptyList(),
                        target = data.target?.mapNotNull targetMap@ {
                            RepositoryAttractionsCategory(
                                id = it.id ?: 0L,
                                name = it.name ?:  return@targetMap null
                            )
                        } ?: emptyList(),
                        service = data.service?.mapNotNull serviceMap@ {
                            RepositoryAttractionsCategory(
                                id = it.id ?: 0L,
                                name = it.name ?:  return@serviceMap null
                            )
                        } ?: emptyList(),
                        friendly = data.friendly?.mapNotNull friendlyMap@ {
                            RepositoryAttractionsCategory(
                                id = it.id ?: 0L,
                                name = it.name ?:  return@friendlyMap null
                            )
                        } ?: emptyList(),
                        images = data.images?.mapNotNull imagesMap@ {
                            RepositoryAttractionsImage(
                                src = it.src ?: return@imagesMap null,
                                subject = it.subject ?: "",
                                ext = it.ext ?: ""
                            )
                        } ?: emptyList(),
                        files = data.files?.mapNotNull filesMap@ {
                            RepositoryAttractionsFile(
                                src = it.src ?: return@filesMap null,
                                subject = it.subject ?: "",
                                ext = it.ext ?: ""
                            )
                        } ?: emptyList(),
                        links = data.links?.mapNotNull linksMap@ {
                            RepositoryAttractionsLink(
                                src = it.src ?: return@linksMap null,
                                subject = it.subject ?: ""
                            )
                        } ?: emptyList(),
                    )
                } ?: emptyList()
            }
        }
    }
}